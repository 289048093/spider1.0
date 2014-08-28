package com.ejushang.spider.service.order;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OrderItem;
import com.ejushang.spider.query.AddOrderItemQuery;
import com.ejushang.spider.query.OrderItemQuery;
import com.ejushang.spider.vo.OrderItemVo;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 下午2:46
 */
public interface IOrderItemService {

    /**
     * 订单项查询：根据查询条件查询订单项
     * @param orderItem 订单项查询条件
     * @return
     */
    public List<OrderItem> findOrderItems(OrderItem orderItem);

    public List<OrderItem> findDetailsOrderItems(String orderNo);

    public List<OrderItemVo> findOrderItemsByOrderNo(String orderNo);


    public Integer findOrderItemCountByOrderNo(String orderNo);
    public Integer findOrderProdNumberByOrderNo(String  orderNo);


    /**
     * 订单项新增
     * @param orderItem 订单项
     */
    public int saveOrderItem(OrderItem orderItem);

    /**
     * 订单项更新
     * @param orderItem 订单项
     */
    public int updateOrderItem(OrderItemQuery orderItem);


    /**
     * 订单项删除
     * @param orderItem
     */
    public int deleteOrderItem(OrderItem orderItem);
    /**
     * 订单项删除
     * @param id Item的ID
     */
    public int deleteOrderItemById(Integer id);

    public List<OrderItem> findOrderItemByOrderId(Integer orderId);

    public List<OrderItemVo> findOrderItemsByOrderId(Integer orderId);

   public OrderItem findOrderItemByProdId(AddOrderItemQuery addOrderItemQuery);

    public int updateOrderItemAddGift(OrderItem orderItem);
}
