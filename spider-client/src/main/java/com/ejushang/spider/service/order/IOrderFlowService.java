package com.ejushang.spider.service.order;

import com.ejushang.spider.constant.OrderStatus;
import com.ejushang.spider.domain.Order;

/**
 * 订单状态改变接口
 * User: liubin
 * Date: 14-1-17
 */
public interface IOrderFlowService {

    /**
     * 改变订单状态,同时触发对应操作
     *
     * @param order 订单实体
     * @param from 原始状态
     * @param to 目标状态
     * @param strict 是否严格限制状态的改变.如果为true,订单状态只能一级一级的改变,
     *               如果为false,订单状态能够跳跃式改变,但是还是不能违反预设条件(例如发货状态不能回滚)
     */
    void changeStatus(Order order, OrderStatus from, OrderStatus to, boolean strict);

    /**
     * 订单确认
     * @param order
     */
    void doConfirm(Order order);

    /**
     * 撤销订单确认
     * @param order
     */
    void cancelConfirm(Order order);

    /**
     * 打印订单
     * @param order
     */
    void doPrint(Order order);

    /**
     * 撤销订单打印
     * @param order
     */
    void cancelPrint(Order order);

    /**
     * 订单验货
     * @param order
     */
    void doExamine(Order order);

    /**
     * 撤销订单验货
     * @param order
     */
    void cancelExamine(Order order);

    /**
     * 订单发货
     * @param order
     */
    void doInvoice(Order order) throws Exception;

    /**
     * 撤销订单发货
     * @param order
     */
    void cancelInvoice(Order order);

    /**
     * 订单作废
     * @param order
     */
    void doInvalid(Order order);

    /**
     * 撤销订单作废
     * @param order
     */
    void cancelInvalid(Order order);
}
