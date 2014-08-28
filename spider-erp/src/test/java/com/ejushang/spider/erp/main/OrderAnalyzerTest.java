package com.ejushang.spider.erp.main;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.erp.main.analyze.OrderAnalyzer;
import com.ejushang.spider.erp.service.order.OrderAnalyzeBaseTest;
import com.ejushang.spider.service.order.IOrderAnalyzeService;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

/**
 * User: liubin
 * Date: 14-1-6
 */
public class OrderAnalyzerTest extends OrderAnalyzeBaseTest {

    private static final Logger log = LoggerFactory.getLogger(OrderAnalyzerTest.class);

    @Autowired
    private IOrderAnalyzeService orderAnalyzeService;
    @Autowired
    private OrderAnalyzer orderAnalyzer;
    @Autowired
    private OriginalOrderHolder ooHolder;

    @Test
    @Rollback
    public void test() throws InterruptedException {
        orderAnalyzer.setUseForTest(true);

        final int iterationCount = 10;
        final List<OriginalOrder> allOriginalOrders = new ArrayList<OriginalOrder>();

        //模拟抓取线程
        Thread fetchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("模拟抓取线程开始构造原始订单");
                for (int i = 0; i < iterationCount; i++) {
                    List<Object> giftParamList = new ArrayList<Object>();
                    List<OriginalOrder> originalOrders = prepareOriginalOrders(giftParamList);
                    allOriginalOrders.addAll(originalOrders);

                    ooHolder.put(originalOrders);
                    try {
                        Thread.sleep(1000 * 5);
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }
                }
            }
        });
        fetchThread.start();

        log.info("分析线程开始分析原始订单");
        orderAnalyzer.start();

        fetchThread.join();

        //等待分析任务处理完
        Thread.sleep(orderAnalyzer.readMinInterval() * 2 + 10 * 1000);

        orderAnalyzer.stop();

        log.info("开始校验原始订单和处理后的结果是否符合预期");

        List<Order> allOrders = orderAnalyzer.getResults();

        assertSaveAnalyzeResultSuccess(allOriginalOrders, allOrders);



    }

    public static int doCalculate(boolean atomic)
    {
        int result = 0;
        if(atomic)
        {
            //do some calculation
        }
        return result;
    }

}
