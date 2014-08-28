package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.bean.LogisticsBean;
import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.constant.OrderFlow;
import com.ejushang.spider.constant.OrderStatus;
import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OrderItem;
import com.ejushang.spider.domain.ShopAuth;
import com.ejushang.spider.erp.api.ILogisticsApi;
import com.ejushang.spider.erp.common.mapper.OrderMapper;
import com.ejushang.spider.erp.common.mapper.ShopAuthMapper;
import com.ejushang.spider.erp.util.OrderUtil;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.query.ShopAuthQuery;
import com.ejushang.spider.service.delivery.ILogisticsInfoService;
import com.ejushang.spider.service.order.IOrderFlowService;
import com.ejushang.spider.service.order.IOrderItemService;
import com.ejushang.spider.service.prodSales.IProdSalesService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.util.DateUtils;
import com.ejushang.spider.util.SessionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 订单状态改变Service
 * User: liubin
 * Date: 14-1-17
 */
@Service
public class OrderFlowService implements IOrderFlowService {

    private static final Logger log = LoggerFactory.getLogger(OrderFlowService.class);

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ShopAuthMapper shopAuthMapper;

    @Resource
    private IProductService productService;

    @Resource
    private IOrderItemService orderItemService;

    @Resource
    private ILogisticsApi logisticsTaoBaoApi;

    @Resource
    private ILogisticsApi logisticsJingDongApi;

    @Resource
    private ILogisticsInfoService logisticsInfoService;

    @Resource
    private IStorageService storageService;

    @Resource
    private IProdSalesService prodSalesService;

    @Override
    @Transactional
    public void changeStatus(Order order, OrderStatus from, OrderStatus to, boolean strict) {
        if (from == to) return;
        if (from == null || to == null) {
            throw new ErpBusinessException(String.format("订单状态变更失败,订单状态为null.原始状态:%s, 目标状态:%s", from, to));
        }
        if (!from.toString().equals(order.getOrderStatus())) {
            throw new ErpBusinessException(String.format("订单状态变更失败,原始订单状态与期待的不一致.原始订单状态:%s, 期待状态:%s", order.getOrderStatus(), from));
        }
        if (from.canBackTo(to, strict)) {
            List<OrderStatus> orderStatusList = to.getAllNodesOnPath(from);
            if (orderStatusList.isEmpty()) {
                log.error(String.format("getAllNodesOnPath查询出来为空集合,程序有bug? 原始状态:%s, 目标状态:%s", from, to));
                return;
            }
            //倒序遍历,最后一个节点不执行方法
            for (int i = orderStatusList.size() - 1; i > 0; i--) {
                OrderStatus orderStatus = orderStatusList.get(i);
                OrderStatus nextOrderStatus = orderStatusList.get(i - 1);
                OrderFlow orderFlow = OrderFlow.getOrderFlow(orderStatus, nextOrderStatus);
                if (orderFlow != null) {
                    callMethod(orderFlow.getCancelMethodName(), order);
                }
            }
            orderMapper.updateOrderStatus(order);

        } else if (from.canNextTo(to, strict)) {
            List<OrderStatus> orderStatusList = from.getAllNodesOnPath(to);
            if (orderStatusList.isEmpty()) {
                log.error(String.format("getAllNodesOnPath查询出来为空集合,程序有bug? 原始状态:%s, 目标状态:%s", from, to));
                return;
            }
            //最后一个节点不执行方法
            for (int i = 0; i < orderStatusList.size() - 1; i++) {
                OrderStatus orderStatus = orderStatusList.get(i);
                OrderStatus nextOrderStatus = orderStatusList.get(i + 1);
                OrderFlow orderFlow = OrderFlow.getOrderFlow(orderStatus, nextOrderStatus);
                if (orderFlow != null) {
                    callMethod(orderFlow.getDoMethodName(), order);
                }
            }
            orderMapper.updateOrderStatus(order);

        } else {
            throw new ErpBusinessException(String.format("订单状态变更失败,不允许变更到指定状态.原始状态:%s, 目标状态:%s", from, to));
        }
    }

    private void callMethod(String methodName, Order order) {
        try {
            Method method = OrderFlowService.class.getMethod(methodName, Order.class);
            method.invoke(this, order);
        } catch (ErpBusinessException e) {
            throw e;
        } catch (Exception e) {
            String errorMsg = "订单状态变更过程中调用方法失败,methodName:" + methodName;
            log.error(errorMsg, e);
            throw new ErpBusinessException("订单操作失败, 请联系管理员");
        }
    }

    @Override
    @Transactional
    public void doConfirm(Order order) {
        OrderUtil.checkSplitStatus(order, "导入进销存");
        order.setOrderStatus(OrderStatus.CONFIRMED.toString());
        order.setConfirmUserId(SessionUtils.getUser().getId());
        order.setConfirmUser(SessionUtils.getUser().getUsername());
        order.setConfirmTime(DateUtils.getCurrentDate());
        List<OrderItem> orderItemList = orderItemService.findOrderItemByOrderId(order.getId());
        if (orderItemList != null) {
            for (OrderItem orderItem : orderItemList) {
                storageService.manipulateStorage(orderItem.getProdId(), -Math.abs(orderItem.getProdCount()));
                prodSalesService.updateProdSalesSaleCount(orderItem.getProdId(), Math.abs(orderItem.getProdCount()));
            }
        }
    }

    @Override
    @Transactional
    public void cancelConfirm(Order order) {
        order.setOrderStatus(OrderStatus.WAIT_PROCESS.toString());
        order.setConfirmUserId(null);
        order.setConfirmUser(null);
        order.setConfirmTime(null);
        order.setShippingNo(null);
        List<OrderItem> orderItemList = orderItemService.findOrderItemByOrderId(order.getId());
        if (orderItemList != null) {
            for (OrderItem orderItem : orderItemList) {
                storageService.manipulateStorage(orderItem.getProdId(), Math.abs(orderItem.getProdCount()));
                prodSalesService.updateProdSalesSaleCount(orderItem.getProdId(), -Math.abs(orderItem.getProdCount()));
            }
        }
    }

    @Override
    @Transactional
    public void doPrint(Order order) {
        order.setOrderStatus(OrderStatus.PRINTED.toString());
        order.setPrintUserId(SessionUtils.getUser().getId());
        order.setPrintUser(SessionUtils.getUser().getUsername());
        order.setPrintTime(DateUtils.getCurrentDate());
    }

    @Override
    @Transactional
    public void cancelPrint(Order order) {
        order.setOrderStatus(OrderStatus.CONFIRMED.toString());
        order.setPrintUserId(null);
        order.setPrintUser(null);
        order.setPrintTime(null);
    }

    @Override
    @Transactional
    public void doExamine(Order order) {
        order.setOrderStatus(OrderStatus.EXAMINED.toString());
        order.setInspectionUserId(SessionUtils.getUser().getId());
        order.setInspectionTime(DateUtils.getCurrentDate());
    }

    @Override
    @Transactional
    public void cancelExamine(Order order) {
        order.setOrderStatus(OrderStatus.PRINTED.toString());
        order.setInspectionUserId(null);
        order.setInspectionTime(null);
    }

    @Override
    @Transactional
    public void doInvoice(Order order) throws Exception {
        if(log.isInfoEnabled()){
            log.info("发货管理：确认发货。"+order);
        }
        order.setOrderStatus(OrderStatus.INVOICED.toString());
        order.setDeliveryUserId(SessionUtils.getUser().getId());
        order.setDeliveryTime(DateUtils.getCurrentDate());

        LogisticsBean logisticsBean = structLogisticsBean(order);
        // 判断订单来源是否为平台新建订单
        if(!StringUtils.isBlank(logisticsBean.getOutOrderNo())){
            // 不为平台新建订单，才进行同步操作
            Boolean flag = false;
            // 判断来自哪个平台
            if(StringUtils.equals(OutPlatformType.TAO_BAO.toDesc(), logisticsBean.getOutPlatform())){
                // 来自淘宝平台
                flag = logisticsTaoBaoApi.sendLogisticsOnline(logisticsBean);
            }
            else if(StringUtils.equals(OutPlatformType.JING_DONG.toDesc(),logisticsBean.getOutPlatform())){
                // 来自京东平台
                flag = logisticsJingDongApi.sendLogisticsOnline(logisticsBean);
            }
        }
        // 向快递100发送物流请求信息
        logisticsInfoService.sendLogisticsInfoRequest(logisticsBean.getOrderNo(), DeliveryType.valueOf(logisticsBean.getExpressCompany()),
                logisticsBean.getExpressNo(),logisticsBean.getReceiveAddress());

    }

    @Override
    @Transactional
    public void cancelInvoice(Order order) {
        throw new UnsupportedOperationException("不能取消发货");
    }

    @Override
    @Transactional
    public void doInvalid(Order order) {
        order.setOrderStatus(OrderStatus.INVALID.toString());
    }

    @Override
    @Transactional
    public void cancelInvalid(Order order) {
        order.setOrderStatus(OrderStatus.WAIT_PROCESS.toString());
    }

    /**
     * 构造logisticsBean信息
     * @param order
     * @return
     */
    private LogisticsBean structLogisticsBean(Order order){
        LogisticsBean logisticsBean = new LogisticsBean();
        // 设置订单号
        logisticsBean.setOrderNo(order.getOrderNo());
        // 设置原始订单号
        logisticsBean.setOutOrderNo(order.getOutOrderNo());
        // 设置外部平台
        logisticsBean.setOutPlatform(order.getOutPlatformType());
        // 设置店铺id
        logisticsBean.setShopId(Long.valueOf(order.getShopId()));
        // 设置物流公司代号
        logisticsBean.setExpressCompany(order.getShippingComp());
        // 设置物流单号
        logisticsBean.setExpressNo(order.getShippingNo());
        // 设置收货人地址
        logisticsBean.setReceiveAddress(StringUtils.trimToEmpty(order.getReceiverState())
                +StringUtils.trimToEmpty(order.getReceiverCity())
                +StringUtils.trimToEmpty(order.getReceiverDistrict())
                +StringUtils.trimToEmpty(order.getReceiverAddress()));

        ShopAuthQuery shopAuthQuery = new ShopAuthQuery();
        shopAuthQuery.setShopId(String.valueOf(order.getShopId()));
        List<ShopAuth> shopAuthList =  shopAuthMapper.findShopAuthByQuery(shopAuthQuery);
        if(CollectionUtils.isEmpty(shopAuthList)){
            throw new ErpBusinessException("shopId:"+order.getShopId()+"的数据不存在");
        }

        ShopAuth shopAuth = shopAuthList.get(0);
        // 设置sessionKey
        logisticsBean.setSessionKey(shopAuth.getSessionKey());
        return logisticsBean;
    }
}
