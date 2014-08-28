package com.ejushang.spider.service.order;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.exception.GenerateException;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.DeliveryPrintVo;
import com.ejushang.spider.vo.OrderInspectionVo;
import com.ejushang.spider.vo.OrderPrintVo;
import com.ejushang.spider.vo.OrderVo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 下午2:46
 */
public interface IOrderService {

    /**
     * 订单查询
     *
     * @return 订单记录集合
     */
    public List<Order> findOrders();

    /**
     * 订单分页
     * @param order
     * @return
     */
    public Page findDetailOrders(OrderQuery order);

    /**
     * 合并订单
     */
public Integer mergeOrder(String orderIds);

    /**
     * 订单新增
     *
     * @param order 订单信息
     */
    public int createOrder(Order order);


    /**
     * 订单删除
     *
     * @param order 订单信息
     */
    public int deleteOrder(Order order);


    /**
     * 通过orderNo查询订单
     *
     * @param orderNo
     * @return
     */
    public Order findOrderByOrderNo(String orderNo);

    /**
     * 通过orderNo查询订单
     *
     * @param id
     * @return
     */
    public Order findOrderById(Integer id);

    /**
     * 通过orderIds 查询物流单打印信息
     *
     * @param orderIds
     * @return 一组物流单打印信息
     */
    public List<DeliveryPrintVo> deliveryPrint(Integer[] orderIds);

    /**
     * 通过orderIds 查询发货单打印信息
     *
     * @param orderIds
     * @return 一组发货单打印信息
     */
    public List<OrderPrintVo> orderPrint(Integer[] orderIds);

    /**
     * 发货单 验证
     *
     * @param shippingNos
     * @return 验货单Vo
     */
    public List<OrderInspectionVo> orderInspection(String[] shippingNos);

    /**
     * 订单作废
     *
     * @param orderIds
     * @return 作废条数
     */
    public Integer orderCancellation(Integer[] orderIds);

    /**
     * 订单恢复
     *
     * @param orderIds
     * @return
     */
    public Integer orderRecover(Integer[] orderIds);

    /**
     * 订单确认 待处理->已确认
     *
     * @param orderIds
     * @return
     */
    public Integer orderConfirm(Integer[] orderIds);

    /**
     * 订单打印 已确认->已打印
     *
     * @param orderIds
     * @return
     */
    public Integer orderAffirmPrint(Integer[] orderIds);

    /**
     * 返回导入 已打印->已确认
     *
     * @param orderIds
     * @return
     */
    public Integer orderBackToConfirm(Integer[] orderIds);

    /**
     * 订单验证 已打印->已验货
     *
     * @param orderIds
     * @return
     */
    public Integer orderBatchExamine(Integer[] orderIds);

    /**
     * 返回已打印 已验货->已打印
     *
     * @param orderIds
     * @return
     */
    public Integer orderBackToPrint(Integer[] orderIds);

    /**
     * 订单发货 已验货->已发货
     *
     * @param orderIds
     * @return
     */
    public Integer orderInvoice(Integer[] orderIds);

    /**
     * 订单返回客服 已验货||已打印||已确认->待处理
     *
     * @param orderIds
     * @return
     */
    public Integer orderBackToWaitProcess(Integer[] orderIds);

    /**
     * 手动拆分订单
     *
     * @param ids
     * @param orderNo
     * @return
     * @throws OrderAnalyzeException
     */
    public String orderByOrderNo(int[] ids, String orderNo) throws OrderAnalyzeException;

    /**
     * @param ids          订单Id数组
     * @param shippingComp 物流表name
     * @param intNo        基数
     * @return 返回1成功，0失败
     */
    public Integer updateOrderShipping(int ids[], String shippingComp, String intNo, String isCover) throws GenerateException;

    public Integer updateOrderShippingNo(Order order);

//    /**
//     * 订单更新：批量更新订单状态功能
//     *
//     * @param order 订单实体
//     * @param ids   订单ID数组
//     * @return
//     */
//
//    public int updateOrderStatusByOrder(Order order, Integer[] ids);

    /**
     * 订单更新：订单信息更新
     *
     * @param order 订单实体
     * @return
     */
    public int updateOrderByOrder(Order order);

    /**
     * 订单逻辑删除
     *
     * @param ids 订单ID数组
     * @return
     */
    public int deleteOrderById(Integer[] ids);

    /**
     * 查询所有 #{指定时间} 以前都 已支付, 却还没有确认发货的订单号
     * <p/>
     * create by Athens on 2014,01,10
     *
     * @param triggerWarnDate 触发时间
     * @return 订单号
     */
    List<Map<String, Object>> findOrderNoLaterPay(String triggerWarnDate);

    /**
     * 查询所有 #{指定时间} 以前都 已发货, 却还没有物流信息的订单号
     *
     * @param triggerWarnDate 触发时间
     *                        create by Athens on 2014,01,10
     * @return 订单号
     */
    List<Map<String, Object>> findNoLogisticsInfoOrderNoLaterSend(String triggerWarnDate);


    /**
     *  查询单个订单详情
     *
     *
     *
     * @param orderNo    订单编号
     * @return      订单（order)，订单的物流信息(expressData)
     */
    OrderVo findSingleDetailOrder(String orderNo) throws Exception;


    /**
     * 根据外部订单号查询订单,不级联查询订单项
     * @param outOrderNo
     * @return
     */
    List<Order> findOrderByOutOrderNo(String outOrderNo);

    /**
     * 导出excel
     * @param orderQuery
     * @return
     */
    Workbook extractOrderAndItem2Excel(OrderQuery orderQuery);
}
