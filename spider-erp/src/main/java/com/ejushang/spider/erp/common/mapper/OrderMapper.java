package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单记录操作类
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 下午2:52
 */
public interface OrderMapper {
    /**
     * 订单查询：根据查询条件查询订单
     *
     * @return 订单集合
     */
    public List<Order> findOrders();

    /**
     * 订单查询：根据查询条件查询订单
     *
     * @param orderQuery 查询条件
     * @return 订单集合
     */

    public List<Order> findOrderByOrderQuery(OrderQuery orderQuery);
    /**
     * 订单查询：根据查询条件查询订单总条数
     *
     * @param orderQuery 查询条件
     * @return 订单集合
     */

    public Integer findOrderCountByOrderQuery(OrderQuery orderQuery);


    /**
     * 订单查询：通过ID查询
     *
     * @param id 订单ID
     * @return order对象
     */
    public Order findOrderById(Integer id);

    /**
     * 通过快递单号查询
     * @param shippingNo
     * @return
     */
    public List<Order> findOrderByShoppingNo(String shippingNo);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    public String findOrderStatusById(Integer id);

    
    /**
     * 通过订单状态查询
     * @param orderStatus
     * @return
     */
    public List<Order> findOrderByStatus(String orderStatus);

    /**
     * 订单新增：添加一条订单记录
     *
     * @param order 订单记录
     */


    public int saveOrder(Order order);


    /**
     * 订单删除：删除一条订单记录
     *
     * @param order 订单记录
     */
    public int deleteOrder(Order order);



    /**
     * 订单查找：通过订单编号查询order
     *
     * @param orderNo 订单编号参数
     * @return Order对象
     */
    public Order findOrderByOrderNo(String orderNo);

    /**
     * 订单更新：通过订单对象order更新物流
     *
     * @param order 订单对象参数
     * @return 影响条数
     */
    public int updateOrderShipping(Order order);

    /**
     * 订单更新：通过订单对象order更新订单状态
     *
     * @param order 订单对象参数
     * @return 影响条数
     */
    public int updateOrderStatus(Order order);

    /**
     * 订单更新：通过订单对象order更新订单
     *
     * @param order 订单对象参数
     * @return 影响条数
     */
    public int updateOrderStatusByOrder(Order order);

    /**
     * 订单更新：通过订单对象order更新订单
     * @param order 订单对象参数
     * @return 影响条数
     */
    public int updateOrderByOrder(Order order);

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    public int deleteOrderById(Integer id);


    /**
     * 查询 #{指定时间} 就已 #{付款} 的订单号
     * <p/>
     * add by Athens on 2014.1.9
     */
    List<Map<String, Object>> findOrderNoByStatusAndWarnDate(Map<String, Object> param);

    /**
     * 查询指定 #{订单类型} #{指定时间} 就已经 #{发货} 的订单号及物流编号
     * <p/>
     * add by Athens on 2014.1.9
     */
    List<Map<String, Object>> findOrderNoByStatusAndTypeAndWarnDate(Map<String, Object> param);

    /**
     * 根据外部订单号查询订单,不级联查询订单项
     * @param outOrderNo
     * @return
     */
    List<Order> findOrderByOutOrderNo(String outOrderNo);

    /**
     * 批量更新备注
     * @param orderIds
     * @param remark
     */
    void updateOrderRemark(@Param("orderIds") Integer[] orderIds, @Param("remark")String remark);

    /**
     * 查询订单，不分页
     * @param orderQuery
     * @return
     */
    List<Order> findOrderByOrderQueryNoPage(OrderQuery orderQuery);
}
