package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OrderItem;
import com.ejushang.spider.query.AddOrderItemQuery;

import java.util.List;

/**
 * 订单项数据操作类
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午5:18
 */
public interface OrderItemMapper {

    /**
     * 订单项查询：根据条件查询订单项
     *
     * @param orderItem
     * @return
     */
    public List<OrderItem> findOrderItems(OrderItem orderItem);

    /**
     * 订单项新增
     *
     * @param orderItem
     */
    public int saveOrderItem(OrderItem orderItem);

    /**
     * 订单项更新
     *
     * @param orderItem
     */
    public int updateOrderItem(OrderItem orderItem);

    /**
     * 通过ID删除orderItem订单项
     *
     * @param id
     * @return
     */
    public int deleteOrderItemById(Integer id);

    /**
     * 通过OrderID删除orderItem订单项
     *
     * @param orderId
     * @return
     */
    public int deleteOrderItemByOrderId(Integer orderId);

    /**
     * 订单项删除
     *
     * @param orderItem
     */
    public int deleteOrderItem(OrderItem orderItem);

    /**
     * 通过orderNo查询OrderItem总条数
     *
     * @param orderNo 订单编号
     * @return 返回OrderItem总条数总条数
     */
    public Integer findOrderItemCountByOrderNo(String orderNo);

    /**
     * 通过商品ID和订单ID查询订单详细
     * @param orderItem
     * @return
     */
    public OrderItem findOrderItemByProdIdAndOrderId(OrderItem orderItem);
    /**
     * 通过orderNo查询OrderItem商品总数量
     *
     * @param orderNo 订单编号
     * @return 返回OrderItem商品总数量
     */
    public Integer findOrderProdNumberByOrderNo(String orderNo);

    /**
     * 通过订单编号查询订单详细
     * @param orderNo 订单编号
     * @return返回 List集合
     */

    public List<OrderItem> findOrderItemByOrderNo(String orderNo);

    /**
     * 通过id查询出订单项
     *
     * @param id
     * @return
     */
    public OrderItem findOrderItemById(Integer id);

    public List<OrderItem> findOrderItemByOrderId(Integer orderId);

    public OrderItem findOrderItemByProdId(AddOrderItemQuery addOrderItemQuery);

    public int updateOrderItemOwn(OrderItem orderItem);

    public int updateOrderItemAddGift(OrderItem orderItem);

}
