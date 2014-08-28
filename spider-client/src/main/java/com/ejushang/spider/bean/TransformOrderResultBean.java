package com.ejushang.spider.bean;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OriginalOrder;

import java.util.List;

/**
 * User: liubin
 * Date: 14-1-2
 */
public class TransformOrderResultBean {

    private List<Order> results;

    private List<OriginalOrder> errorOriginalOrders;

    private List<String> errorInfoList;

    public TransformOrderResultBean(List<Order> results, List<OriginalOrder> errorOriginalOrders, List<String> errorInfoList) {
        this.results = results;
        this.errorOriginalOrders = errorOriginalOrders;
        this.errorInfoList = errorInfoList;
    }

    public List<Order> getResults() {
        return results;
    }

    public List<OriginalOrder> getErrorOriginalOrders() {
        return errorOriginalOrders;
    }

    public List<String> getErrorInfoList() {
        return errorInfoList;
    }
}
