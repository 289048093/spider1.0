package com.ejushang.spider.service.order;

import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.query.OrderFetchQuery;
import com.ejushang.spider.util.Page;

import java.lang.reflect.InvocationTargetException;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 下午2:46
 */
public interface IOrderFetchService {

    /**
     * 订单抓取查询：根据查询条件查询订单抓取
     * @param orderFetchQuery 订单抓取查询条件
     * @return
     */
    public Page findOrderFetchs(OrderFetchQuery orderFetchQuery)throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 订单抓取新增
     * @param orderFetch 订单抓取
     */
    public int saveOrderFetch(OrderFetch orderFetch);

    /**
     * 订单抓取更新
     * @param orderFetch 订单抓取
     */
    public int updateOrderFetch(OrderFetch orderFetch);

    /**
     * 订单抓取删除
     * @param orderFetch
     */
    public int deleteOrderFetch(OrderFetch orderFetch);

    /**
     * 获取最后一条订单抓取记录（根据抓取时间）
     * @return
     */
    public OrderFetch findLastOrderFetch(String outPlatformType,String shopId);
}
