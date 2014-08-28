package com.ejushang.spider.erp.main;

import com.ejushang.spider.erp.main.analyze.OrderAnalyzer;
import com.ejushang.spider.erp.main.fetch.OrderFetcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单抓取分析入口
 * User: liubin
 * Date: 13-12-26
 */
public class OrderHouseKeeper implements OrderFetchMBean {

    @Autowired
    private OrderAnalyzer orderAnalyzer;
    @Autowired
    private OrderFetcher orderFetcher;

    @Override
    public void start() {
        orderFetcher.start();
        orderAnalyzer.start();
    }

    @Override
    public void stop() {
        orderFetcher.stop();
        orderAnalyzer.stop();
    }

    @Override
    public String getStatus() {
        String fetcherStatus = orderFetcher.getStatus().get().toString();
        String analyzerStatus = orderAnalyzer.getStatus().get().toString();
        String result = String.format("fetcher status:%s, analyzer status:%s", fetcherStatus, analyzerStatus);
        return result;
    }
}
