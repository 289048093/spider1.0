package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.constant.OrderStatus;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.common.mapper.*;
import com.ejushang.spider.query.AddOrderItemQuery;
import com.ejushang.spider.query.OrderItemQuery;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.service.order.IOrderItemService;
import com.ejushang.spider.util.Money;
import com.ejushang.spider.vo.OrderItemVo;
import com.ejushang.spider.vo.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午5:14
 */
@Service
public class OrderItemService implements IOrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private StorageMapper storageMapper;
    @Autowired
    private ProdCategoryMapper prodCategoryMapper;

    /**
     * 订单查询
     *
     * @param orderItem 订单项查询条件
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrderItem> findOrderItems(OrderItem orderItem) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findOrderItems 传值OrderItem类orderItem[" + orderItem + "]");
        }
        return orderItemMapper.findOrderItems(orderItem);
    }


    /**
     * 根据给定的订单编号，查询对应的数据
     * 修改人：Json.zhu
     *
     * @param orderNo
     * @return
     */
    public List<OrderItem> findDetailsOrderItems(String orderNo) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findDetailsOrderItems 传值String类orderNo[" + orderNo + "]");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo(orderNo);
        List<OrderItem> orderItemList = findOrderItems(orderItem);
        for (OrderItem oi : orderItemList) {
            Product product = productMapper.findProductById(oi.getProdId());
            oi.setSellPropsStr(product == null ? " " : product.getDescription());//商品属性.通过id
            Storage storage = storageMapper.findStorageByProdId(oi.getProdId());
            Brand brand = brandMapper.findBrandById(oi.getProdId());
            oi.setBrandName(brand == null ? " " : brand.getName());   //品牌
        }
        return orderItemList;
    }

    @Override
    public List<OrderItemVo> findOrderItemsByOrderNo(String orderNo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * //     * 通过orderNo查询订单详细
     *
     * @param orderId
     * @return 一个OrderItemVo的list
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderItemVo> findOrderItemsByOrderId(Integer orderId) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findOrderItemsByOrderNo 传值Integer类orderId[" + orderId + "]");
        }
        List<OrderItemVo> result = new ArrayList<OrderItemVo>();
        OrderItemVo orderItemVo = null;
        OrderItem o = new OrderItem();
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(orderId);
        if (orderItemList.size() > 0) {
            for (OrderItem orderItem : orderItemList) {
                orderItemVo = new OrderItemVo();
                orderItemVo.setId(orderItem.getId());
                orderItemVo.setProdNo(orderItem.getProdCode());
                orderItemVo.setSkuCode(orderItem.getSkuCode());
                orderItemVo.setProdName(orderItem.getProdName());
                orderItemVo.setTotalFee(Money.CentToYuan(Long.valueOf(orderItem.getTotalFee())).toString());
                orderItemVo.setActualFee(Money.CentToYuan(Long.valueOf(orderItem.getActualFee())).toString());
                orderItemVo.setPriceDescription(orderItem.getPriceDescription());
                orderItemVo.setProdPrice(Money.getMoneyString(orderItem.getProdPrice()));
                orderItemVo.setProdCount(orderItem.getProdCount());
                orderItemVo.setOrderNo(orderItem.getOrderNo());
                orderItemVo.setProdId(orderItem.getProdId());
                orderItemVo.setItemType(orderItem.getItemType());
                orderItemVo.setOutOrderNo(orderItem.getOutOrderNo());
                orderItemVo.setOrderId(orderItem.getOrderId());
                if (orderItem.getPostFee() != null) {
                    orderItemVo.setPostFee(Money.CentToYuan(orderItem.getPostFee()) + "");
                }
                Product product = productMapper.findProductById(orderItem.getProdId());
                if (null != product) {
                    orderItemVo.setBrandId(product.getBrandId());
                    orderItemVo.setCid(product.getCid());
                    orderItemVo.setDescription(product.getDescription());

                    Brand brand = brandMapper.findBrandById(product.getBrandId());
                    ProdCategory prodCategory = prodCategoryMapper.findProdCategoryById(product.getCid());
                    if (null != brand) {

                        orderItemVo.setBrandName(brand.getName());
                    }
                    if (null != prodCategory) {

                        orderItemVo.setProdCateName(prodCategory.getName());
                    }
                }

                Storage storage = storageMapper.findStorageByProdId(orderItem.getProdId());
                if (null != storage) {
                    orderItemVo.setActuallyNumber(storage.getActuallyNumber());
                    orderItemVo.setStorageId(storage.getId());
                }
                result.add(orderItemVo);
            }


        }

        return result;

    }

    @Override
    @Transactional(readOnly = true)
    public Integer findOrderItemCountByOrderNo(String orderNo) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findOrderItemCountByOrderNo 传值String类orderNo[" + orderNo + "]");
        }
        return orderItemMapper.findOrderItemCountByOrderNo(orderNo);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findOrderProdNumberByOrderNo(String orderNo) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findOrderProdNumberByOrderNo 传值String类orderNo[" + orderNo + "]");
        }
        return orderItemMapper.findOrderProdNumberByOrderNo(orderNo);
    }


    /**
     * 订单新增
     *
     * @param orderItem 订单项
     */
    @Transactional
    public int saveOrderItem(OrderItem orderItem) {
        return orderItemMapper.saveOrderItem(orderItem);
    }

    /**
     * 订单更新
     *
     * @param orderItemQuery 订单项
     */
    @Transactional
    public int updateOrderItem(OrderItemQuery orderItemQuery) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService updateOrderItem 传值String类orderNo[" + orderItemQuery + "]");
        }
        int result = 1;
        int one = 0;
        int two = 0;
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setId(orderItemQuery.getId());
        OrderItem orderItem = orderItemMapper.findOrderItemById(orderItemQuery.getId());
        if (orderItem == null) {
            return 0;
        }
        Order orde = orderMapper.findOrderById(orderItem.getOrderId());
        if (!orde.getOrderStatus().equals(OrderStatus.WAIT_PROCESS.toString())) {
            return 2;
        }
        if(orderItemQuery.getActualFee()!=null){
            newOrderItem.setActualFee(Money.YuanToCent(orderItemQuery.getActualFee()));
        }
        if(orderItemQuery.getPostFee()!=null){
            System.out.println("邮费:"+orderItemQuery.getPostFee());
            newOrderItem.setPostFee(Money.YuanToCent(orderItemQuery.getPostFee()));
        }
        if(orderItemQuery.getProdCount()!=null){
            newOrderItem.setTotalFee(orderItemQuery.getProdCount() * orderItem.getProdPrice());
        }
        if(orderItemQuery.getPriceDescription()!=null)
        {
            newOrderItem.setPriceDescription(orderItemQuery.getPriceDescription());
        }
        one = orderItemMapper.updateOrderItem(newOrderItem);
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(orderItem.getOrderId());
        Order order = orderMapper.findOrderById(orderItem.getOrderId());
        order.setOrderItemList(orderItemList);
        order.calPayment();
        if (one == 1) {

            two = orderMapper.updateOrderByOrder(order);
        }

        if (one == 0 || two == 0) {
            result = 0;
        }

        return result;
    }

    /**
     * 订单删除
     *
     * @param orderItem
     */
    @Transactional
    public int deleteOrderItem(OrderItem orderItem) {
        return orderItemMapper.deleteOrderItem(orderItem);
    }

    /**
     * 订单详细删除
     *
     * @param id Item的ID
     * @return
     */
    @Override
    @Transactional
    public int deleteOrderItemById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService deleteOrderItemById的参数id[" + id + "]");
        }
        int result = 1;
        OrderItem orderItem = orderItemMapper.findOrderItemById(id);
           orderItemMapper.deleteOrderItemById(id);
        Order order = orderMapper.findOrderById(orderItem.getOrderId());
        if(order==null){
            return 0;
        }
         List<OrderItem> orderItemList=orderItemMapper.findOrderItemByOrderId(order.getId());
        order.setOrderItemList(orderItemList);
        order.calPayment();
        orderMapper.updateOrderByOrder(order);
        return result;

    }

    @Override
    public List<OrderItem> findOrderItemByOrderId(Integer orderId) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findOrderItemByOrderId的参数orderId[" + orderId + "]");
        }
        return orderItemMapper.findOrderItemByOrderId(orderId);
    }

    @Override
    public OrderItem findOrderItemByProdId(AddOrderItemQuery addOrderItemQuery) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService findOrderItemByProdId的参数AddOrderItemQuery[" + addOrderItemQuery.toString() + "]");
        }
        return orderItemMapper.findOrderItemByProdId(addOrderItemQuery);
    }

    @Override
    public int updateOrderItemAddGift(OrderItem orderItem) {
        if (log.isInfoEnabled()) {
            log.info("OrderItemService updateOrderItemAddGift的参数OrderItem[" + orderItem.toString() + "]");
        }
        orderItemMapper.updateOrderItemAddGift(orderItem);
        return 0;
    }
}
