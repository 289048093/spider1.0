package com.ejushang.spider.vo;

import java.util.Date;
import java.util.List;

/**
 * User: Blomer
 * Date: 14-1-10
 * Time: 上午11:05
 */
public class DeliveryPrintVo {

    /**
     * 仓库负责人
     */
    private String chargePerson;
    /**
     * 仓库地址
     */
    private String repoAddress;
    /**
     * 责任人电话
     */
    private String chargeMobile;
    /**
     * 负责人手机号
     */
    private String chargePhone;
    /**
     * 仓库名
     */
    private String repoName;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人手机
     */
    private String receiverPhone;
    /**
     * 收货人电话
     */
    private String receiverMobile;
    /**
     * 收货人省份
     */
    private String receiverState;
    /**
     * 收货人城市
     */
    private String receiverCity;
    /**
     * 收货人地区
     */
    private String receiverDistrict;
    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 收货人邮编
     */
    private String receiverZip;
    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 订单项
     */
    private List<OrderItemPrintVo> orderItemPrintVos;

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

    public String getChargeMobile() {
        return chargeMobile;
    }

    public void setChargeMobile(String chargeMobile) {
        this.chargeMobile = chargeMobile;
    }

    public String getChargePhone() {
        return chargePhone;
    }

    public void setChargePhone(String chargePhone) {
        this.chargePhone = chargePhone;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
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

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<OrderItemPrintVo> getOrderItemPrintVos() {
        return orderItemPrintVos;
    }

    public void setOrderItemPrintVos(List<OrderItemPrintVo> orderItemPrintVos) {
        this.orderItemPrintVos = orderItemPrintVos;
    }
}
