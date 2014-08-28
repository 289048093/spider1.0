package com.ejushang.spider.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User:moon
 * Date: 14-1-15
 * Time: 上午11:20
 */
public class AddOrderVo {

    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 买家ID，即买家的淘宝号
     */
    private String buyerId;
    /**
     * 买家留言
     */
    private String buyerMessage;
    /**
     * 客服人员的备注
     */
    private String remark;
    /**
     * 收货人的姓名
     */
    private String receiverName;
    /**
     * 收货人的电话号码
     */
    private String receiverPhone;
    /**
     * 收货人的手机号码
     */
    private String receiverMobile;
    /**
     * 收货人的邮编
     */
    private String receiverZip;
    /**
     * 收货人的所在省份
     */
    private String receiverState;
    /**
     * 收货人的所在城市
     */
    private String receiverCity;
    /**
     * 收货人的所在地区
     */
    private String receiverDistrict;
    /**
     * 收货人的详细地址
     */
    private String receiverAddress;

    /**
     * 物流公司
     */
    private String shippingComp;

    /**
     * 店铺Id
     */
    private Integer shopId;

    /**
     * 成交总金额
     */
  //  private Double totalFee;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getShippingComp() {
        return shippingComp;
    }

    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

//    public Double getTotalFee() {
//        return totalFee;
//    }
//
//    public void setTotalFee(Double totalFee) {
//        this.totalFee = totalFee;
//    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("buyId", this.buyerId)
                .append("buyerMessage", this.buyerMessage)
                .append("receiverPhone",this.receiverPhone)
                .append("shopId",this.shopId).toString();
    }
}
