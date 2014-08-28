package com.ejushang.spider.vo;

import java.util.List;

/**
 * User: Blomer
 * Date: 14-1-13
 * Time: 上午10:38
 */
public class OrderInspectionVo {

    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 物流编号
     */
    private String shippingNo;
    /**
     * 订单状态
     */
     private String orderStatus;

    /**
     * 物流公司
     */
    private String shippingComp;
    /**
     * 收货人
     */
    private String receiverName;
    /**
     * 确认人名字
     */
    private String confirmUser;

    /**
     * 客服留言
     */
    private String remark;
    /**
     * 买家留言
     */
    private String buyerMessage;
    /**
     * 订单项
     */
    private List<OrderItemInspectionVo> orderItemInspectionVos;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingComp() {
        return shippingComp;
    }

    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public List<OrderItemInspectionVo> getOrderItemInspectionVos() {
        return orderItemInspectionVos;
    }

    public void setOrderItemInspectionVos(List<OrderItemInspectionVo> orderItemInspectionVos) {
        this.orderItemInspectionVos = orderItemInspectionVos;
    }
}
