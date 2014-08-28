package com.ejushang.spider.vo;

import java.util.Date;
import java.util.List;

/**
 * User: Blomer
 * Date: 14-1-10
 * Time: 下午2:49
 */
public class OrderPrintVo {
    /**
     * 物流编号
     */
    private String shippingNo;
    /**
     * 收货人
     */
    private String receiverName;
    /**
     * 收货人联系方式 1
     */
    private String receiverPhone;
    /**
     * 收货人联系方式 2
     */
    private String receiverMobile;
    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 买家留言
     */
    private String buyerMessage;
    /**
     * 物流公司
     */
    private String shippingComp;
    /**
     * 订购日期
     */
    private Date payTime;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 原始订单编号
     */
    private String outOrderNo;
    /**
     * 订单项
     */
    private List<OrderItemPrintVo> orderItemPrintVos;
    /**
     * 合计
     */
    private String finalTotalFee;
    /**
     * 发货人
     */
    private String chargePerson;
    /**
     * 发货地址
     */
    private String repoAddress;
    /**
     * 客服留言
     */
    private String remark;

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getShippingComp() {
        return shippingComp;
    }

    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public List<OrderItemPrintVo> getOrderItemPrintVos() {
        return orderItemPrintVos;
    }

    public void setOrderItemPrintVos(List<OrderItemPrintVo> orderItemPrintVos) {
        this.orderItemPrintVos = orderItemPrintVos;
    }

    public String getFinalTotalFee() {
        return finalTotalFee;
    }

    public void setFinalTotalFee(String finalTotalFee) {
        this.finalTotalFee = finalTotalFee;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getRepoAddress() {
        return repoAddress;
    }

    public void setRepoAddress(String repoAddress) {
        this.repoAddress = repoAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
