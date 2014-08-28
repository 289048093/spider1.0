package com.ejushang.spider.erp.main.fetch;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.constant.Constant;
import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.erp.api.IOrderApi;
import com.ejushang.spider.erp.main.OrderFetchLifecycle;
import com.ejushang.spider.erp.main.OriginalOrderHolder;
import com.ejushang.spider.erp.main.WorkerStatus;
import com.ejushang.spider.erp.util.SystemConfConstant;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.service.sysconfig.IConfService;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.ejushang.spider.util.AppConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 源订单抓取主线程
 * User: liubin
 * Date: 13-12-27
 */
@Component
public class OrderFetcher implements OrderFetchLifecycle,Runnable {

    private static final Logger log = LoggerFactory.getLogger(OrderFetch.class);

    // 查询间隔时间，单位：分钟
    private static final Integer timeInterval = 1 * 60;

    private AtomicReference<WorkerStatus> status = new AtomicReference<WorkerStatus>(WorkerStatus.STOPPED);

    @Autowired
    private OriginalOrderHolder ooHolder;

    @Autowired
    private IOrderApi orderTaoBaoApi;

    @Autowired
    private IOrderApi orderJingDongApi;

    @Autowired
    private IShopService shopService;

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
        while(WorkerStatus.RUNNING == status.get()) {
            List<OriginalOrder> originalOrders = new ArrayList<OriginalOrder>();
            List<OriginalOrder> subOriginalOrders = null;
            List<ShopBean> shopBeanList = shopService.findAllShopBean();
            for(ShopBean shopBean : shopBeanList){
                try {
                    if(StringUtils.equals(OutPlatformType.TAO_BAO.toDesc(),shopBean.getOutPlatformType())){
                        String orderFetchType = AppConfigUtil.getInstance("erp_config.properties").getProperty("order_fetch_type");
                        if(StringUtils.equals(orderFetchType, Constant.OrderFetchType.API)){
                            subOriginalOrders = orderTaoBaoApi.fetchOrder(shopBean);
                        }
                        else if(StringUtils.equals(orderFetchType,Constant.OrderFetchType.JST)){
                            subOriginalOrders = orderTaoBaoApi.fetchOrderByJst(shopBean);
                        }
                        else{
                            subOriginalOrders = orderTaoBaoApi.fetchOrder(shopBean);
                        }

                    }else if(StringUtils.equals(OutPlatformType.JING_DONG.toDesc(),shopBean.getOutPlatformType())){
                        subOriginalOrders = orderJingDongApi.fetchOrder(shopBean);
                    }
                    if(!CollectionUtils.isEmpty(subOriginalOrders)){
                        originalOrders.addAll(subOriginalOrders);
                    }
                } catch (Exception e) {
                    if(log.isWarnEnabled()){
                        log.error("订单抓取:调用orderApi.fetchOrder("+shopBean+")出现异常,继续获取……"+e);
                    }
                    continue;
                }
            }

            log.info("订单抓取:抓取到的订单项列表:" + originalOrders);
            // 将原始订单项置入队列
            if(originalOrders != null && originalOrders.size() > 0){
                log.info("订单抓取:将原始订单项置入队列:" + originalOrders);
                ooHolder.put(originalOrders);
            }
            try {
                if(log.isInfoEnabled()){
                    log.info(String.format("%s is running, hashcode:%d", this.getClass().getSimpleName(), this.hashCode()));
                }
                Thread.sleep(readMinInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        status.compareAndSet(WorkerStatus.STOP_WAITING, WorkerStatus.STOPPED);
    }

    private long readMinInterval() {
        int result = timeInterval;
        Integer value = confService.findConfIntegerValue(SystemConfConstant.ORDER_FETCH_INTERVAL);
        if(value != null) {
            result = value;
        }
        return result * 1000;
    }

    public AtomicReference<WorkerStatus> getStatus() {
        return status;
    }
}
