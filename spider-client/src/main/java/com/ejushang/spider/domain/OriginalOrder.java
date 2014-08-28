package com.ejushang.spider.domain;

import java.util.Date;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午10:58
 */
public class OriginalOrder {

    /** 主键编号 */
    private Integer id;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 商品金额（商品价格乘以数量的总金额）
     */
    private Long totalFee;
    /** 建议使用trade.promotion_details查询系统优惠 系统优惠金额（如打折，VIP，满就送等） */
    private Long discountFee;
    /** 买家使用积分,下单时生成，且一直不变 */
    private Long pointFee;
    /** 是否包含邮费 */
    private Boolean hasPostFee;
    /** 交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零） */
    private Long availableConfirmFee;
    /** 买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0 */
    private Long realPointFee;
    /** 实付金额 */
    private Long payment;
    /** 邮费 */
    private Long postFee;
    /** 卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额） */
    private Long receivedPayment;
    /** 手工调整金额 */
    private Long adjustFee;
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
     * 下单时间
     */
    private Date buyTime;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 订单来自那个平台（如天猫，京东）
     */
    private String outPlatformType;
    /**
     * 外部系统的订单号（如天猫）
     */
    private String outOrderNo;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 是否发票
     */
    private Boolean needInvoice;
    /**
     * 发票抬头
     */
    private String invoiceName;
    /**
     * 发票内容
     */
    private String invoiceContent;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 记录更新时间
     */
    private Date updateTime;
    /**
     * 该源订单是否被订单处理程序处理过
     */
    private Boolean processed;

    /**
     * 订单项集合
     */
    private List<OriginalOrderItem> originalOrderItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public Long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    public Long getPointFee() {
        return pointFee;
    }

    public void setPointFee(Long pointFee) {
        this.pointFee = pointFee;
    }

    public Boolean getHasPostFee() {
        return hasPostFee;
    }

    public void setHasPostFee(Boolean hasPostFee) {
        this.hasPostFee = hasPostFee;
    }

    public Long getAvailableConfirmFee() {
        return availableConfirmFee;
    }

    public void setAvailableConfirmFee(Long availableConfirmFee) {
        this.availableConfirmFee = availableConfirmFee;
    }

    public Long getRealPointFee() {
        return realPointFee;
    }

    public void setRealPointFee(Long realPointFee) {
        this.realPointFee = realPointFee;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public Long getPostFee() {
        return postFee;
    }

    public void setPostFee(Long postFee) {
        this.postFee = postFee;
    }

    public Long getReceivedPayment() {
        return receivedPayment;
    }

    public void setReceivedPayment(Long receivedPayment) {
        this.receivedPayment = receivedPayment;
    }

    public Long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(Long adjustFee) {
        this.adjustFee = adjustFee;
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

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOutPlatformType() {
        return outPlatformType;
    }

    public void setOutPlatformType(String outPlatformType) {
        this.outPlatformType = outPlatformType;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Boolean getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Boolean needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public List<OriginalOrderItem> getOriginalOrderItems() {
        return originalOrderItems;
    }

    public void setOriginalOrderItems(List<OriginalOrderItem> originalOrderItems) {
        this.originalOrderItems = originalOrderItems;
    }

    public String toString() {
        return "OriginalOrder={" +
                "id = " + id + "," +
                "status = " + status + "," +
                "totalFee = " + totalFee + "," +
                "buyerMessage = " + buyerMessage + "," +
                "remark = " + remark + "," +
                "buyerId = " + buyerId + "," +
                "receiverName = " + receiverName + "," +
                "receiverPhone = " + receiverPhone + "," +
                "receiverMobile = " + receiverMobile + "," +
                "receiverZip = " + receiverZip + "," +
                "receiverState = " + receiverState + "," +
                "receiverCity = " + receiverCity + "," +
                "receiverDistrict = " + receiverDistrict + "," +
                "receiverAddress = " + receiverAddress + "," +
                "buyTime = " + buyTime + "," +
                "payTime = " + payTime + "," +
                "outPlatformType = " + outPlatformType + "," +
                "outOrderNo = " + outOrderNo + "," +
                "shopId = " + shopId + "," +
                "shopName = " + shopName + "," +
                "needInvoice = " + needInvoice + "," +
                "invoiceName = " + invoiceName + "," +
                "invoiceContent = " + invoiceContent + "," +
                "createTime = " + createTime + "," +
                "updateTime = " + updateTime + "," +
                "processed = " + processed + "," +
                "}";

    }
}
