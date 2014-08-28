package com.ejushang.spider.erp.main.analyze;

import com.ejushang.spider.bean.TransformOrderResultBean;
import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.erp.common.mapper.OrderMapper;
import com.ejushang.spider.erp.main.OrderFetchLifecycle;
import com.ejushang.spider.erp.main.OriginalOrderHolder;
import com.ejushang.spider.erp.main.WorkerStatus;
import com.ejushang.spider.erp.util.SystemConfConstant;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.service.order.IOrderAnalyzeService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.service.order.IOriginalOrderService;
import com.ejushang.spider.service.sysconfig.IConfService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 源订单分析处理主线程
 * User: liubin
 * Date: 13-12-27
 */
@Component
public class OrderAnalyzer implements OrderFetchLifecycle,Runnable {

    private final Logger log = LoggerFactory.getLogger(OrderAnalyzer.class);

    private AtomicReference<WorkerStatus> status = new AtomicReference<WorkerStatus>(WorkerStatus.STOPPED);

    private static final Integer timeInterval = 1 * 60;

    //单元测试开关
    private volatile boolean useForTest = false;
    private List<Order> results = new ArrayList<Order>();

    @Autowired
    private OriginalOrderHolder ooHolder;
    @Autowired
    private IOriginalOrderService originalOrderService;
    @Autowired
    private IOrderAnalyzeService orderAnalyzeService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IConfService confService;

    @Override
    public void start() {
        if(status.compareAndSet(WorkerStatus.STOPPED, WorkerStatus.RUNNING)) {
            log.info("start analyzer");
            new Thread(this).start();
        }
    }

    @Override
    public void stop() {
        if(status.compareAndSet(WorkerStatus.RUNNING, WorkerStatus.STOP_WAITING)) {
            log.info("stop analyzer");
        }
    }

    @Override
    public void run() {
        boolean firstTime = true;
        StopWatch sw = new StopWatch();
        long minInterval = readMinInterval();

        while(WorkerStatus.RUNNING == status.get()) {
            try {
                log.info("订单分析任务开始执行", sw.toString());
                sw.reset();
                sw.start();

                if(!useForTest) {
                    analyze(firstTime);
                } else {
                    //集合的结果用作测试
                    results.addAll(analyze(firstTime));
                }
                firstTime = false;

                sw.stop();
                log.info("订单分析任务执行耗时{}", sw.toString());
                long sleepTime = minInterval - sw.getTime();
                if(sleepTime > 0) {
                    try {
                        log.info("订单分析任务休眠{}ms", sleepTime);
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }
                }
            } catch (Exception e) {
                log.error("执行订单分析任务的时候发生未知错误", e);
            }
        }
        status.compareAndSet(WorkerStatus.STOP_WAITING, WorkerStatus.STOPPED);
    }

    public long readMinInterval() {
        int result = timeInterval;
        Integer value = confService.findConfIntegerValue(SystemConfConstant.ORDER_ANALYZE_INTERVAL);
        if(value != null) {
            result = value;
        }
        return result * 1000;
    }

    public List<Order> analyze(boolean firstTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<List<OriginalOrder>> msgList = ooHolder.fetchAll();
        List<OriginalOrder> allOriginalOrders = new ArrayList<OriginalOrder>();
        if(!msgList.isEmpty()) {
            log.info("从OriginalOrderHolder中读取到{}条数据", msgList.size());
            for(List<OriginalOrder> originalOrders : msgList) {
                allOriginalOrders.addAll(originalOrders);
            }
        } else {
            log.info("OriginalOrderHolder中的数据为空");
        }
        if(firstTime) {
            //判断是第一次运行程序,读取OriginalOrder表中processed为false的源订单
            //防止出现因断电导致队列中的数据丢失的情况
            List<OriginalOrder> unprocessdOriginalOrders = originalOrderService.findOriginalOrders(false);
            //将数据库中的oo与队列中的合并,id相同的即忽略
            Set<Integer> idSet = new HashSet<Integer>();
            for(OriginalOrder oo : allOriginalOrders) {
                idSet.add(oo.getId());
            }
            for(OriginalOrder oo : unprocessdOriginalOrders) {
                if(!idSet.contains(oo.getId())) {
                    allOriginalOrders.add(oo);
                }
            }
        }

        return analyzeOriginalOrders(allOriginalOrders);
    }

    /**
     * 对抓取得到的源订单进行处理
     * @param allOriginalOrders
     * @return
     */
    public List<Order> analyzeOriginalOrders(List<OriginalOrder> allOriginalOrders) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if(allOriginalOrders.isEmpty()) {
            return new ArrayList<Order>();
        }
        //TODO 对源订单根据创建时间排序

        List<OriginalOrder> needAnalyzeOriginalOrders = new ArrayList<OriginalOrder>(allOriginalOrders);

        orderAnalyzeService.preHandleOriginalOrders(needAnalyzeOriginalOrders);

        //转化成系统内的订单
        TransformOrderResultBean transformOrderResultBean = orderAnalyzeService.transformToOrder(needAnalyzeOriginalOrders);
        List<OriginalOrder> errorOriginalOrders = transformOrderResultBean.getErrorOriginalOrders();
        List<Order> orders = transformOrderResultBean.getResults();

        //对orders进行加赠品与拆合
        try {
            orders = orderAnalyzeService.prepareImport(orders);
        } catch (OrderAnalyzeException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        //构造需要设置processed为true的original order id数组
        Set<Integer> processedOriginalOrderIdSet = new HashSet<Integer>(allOriginalOrders.size());
        Set<Integer> errorOriginalOrderIdSet = new HashSet<Integer>(errorOriginalOrders.size());
        for(OriginalOrder errorOriginalOrder : errorOriginalOrders) {
            errorOriginalOrderIdSet.add(errorOriginalOrder.getId());
        }
        for(OriginalOrder originalOrder : allOriginalOrders) {
            if(!errorOriginalOrderIdSet.contains(originalOrder.getId())) {
                processedOriginalOrderIdSet.add(originalOrder.getId());
            }
        }

        //将处理后的订单持久化到数据库,并且修改原始订单为已处理
        orderAnalyzeService.saveAnalyzeResults(orders, processedOriginalOrderIdSet);
        return orders;
    }


    public void setUseForTest(boolean useForTest) {
        this.useForTest = useForTest;
    }

    public List<Order> getResults() {
        return results;
    }

    public AtomicReference<WorkerStatus> getStatus() {
        return status;
    }
}
