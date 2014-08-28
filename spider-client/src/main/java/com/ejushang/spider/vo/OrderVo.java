package com.ejushang.spider.vo;

import com.ejushang.spider.domain.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: tin
 * Date: 14-1-2
 * Time: 下午4:59
 */
public class OrderVo {

    /**
     * 主键编号
     */
    private Integer id;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 成交总金额
     */
    private String totalFee;
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
     * 物流编号
     */
    private String shippingNo;
    /**
     * 物流公司
     */
    private String shippingComp;
    /**
     * 库房ID
     */
    private Integer repoId;
    /**
     * 库房名称
     */
    private String repoName;
    /**
     * 下单时间
     */
    private Date buyTime;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 审核确定时间
     */
    private Date confirmTime;
    /**
     * 审核人ID
     */
    private Integer confirmUserId;
    /**
     * 审核人名称
     */
    private String confirmUser;
    /**
     * 打印时间
     */
    private Date printTime;
    /**
     * 打印人id
     */
    private Integer printUserId;
    /**
     * 打印人
     */
    private String printUser;
    /**
     * 验货时间
     */
    private Date inspectionTime;

    /**
     * 验货人ID
     */
    private Integer inspectionUserId;
    /**
     * 发货时间
     */
    private Date deliveryTime;
    /**
     * 发货人ID
     */
    private Integer deliveryUserId;
    /**
     * 签收时间
     */
    private Date receiptTime;


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
    private Integer shopId;
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
     * 原始订单ID
     */
    private Integer originalOrderId;
    /**
     * 商品名称
     */
     private String itemName;
    /**
     * 邮费
     */
    private String postFee;
    /**
     * 实际金额
     */
    private String payment;

    /**
     * 物流信息
     */
    private List<LinkedHashMap<String, String>> expressInfoData;

    public List<LinkedHashMap<String, String>> getExpressInfoData() {
        return expressInfoData;
    }

    public void setExpressInfoData(List<LinkedHashMap<String, String>> expressInfoData) {
        this.expressInfoData = expressInfoData;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
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

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getShippingComp() {
        return shippingComp;
    }

    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
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

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Integer confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public Integer getPrintUserId() {
        return printUserId;
    }

    public void setPrintUserId(Integer printUserId) {
        this.printUserId = printUserId;
    }

    public String getPrintUser() {
        return printUser;
    }

    public void setPrintUser(String printUser) {
        this.printUser = printUser;
    }

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public Integer getInspectionUserId() {
        return inspectionUserId;
    }

    public void setInspectionUserId(Integer inspectionUserId) {
        this.inspectionUserId = inspectionUserId;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryUserId() {
        return deliveryUserId;
    }

    public void setDeliveryUserId(Integer deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
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

    public Integer getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(Integer originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    /**
     * orderItem 实体
     */

    private List<OrderItem> orderItem;
    /**
     * order的item条数
     */
    private Integer itemCount =0;
    /**
     * order的所购商品总数量
     */
    private Integer itemNumCount =0;

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getItemNumCount() {
        return itemNumCount;
    }

    public void setItemNumCount(Integer itemNumCount) {
        this.itemNumCount = itemNumCount;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", buyerMessage='" + buyerMessage + '\'' +
                ", remark='" + remark + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                ", receiverState='" + receiverState + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverDistrict='" + receiverDistrict + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", shippingNo='" + shippingNo + '\'' +
                ", shippingComp='" + shippingComp + '\'' +
                ", repoId=" + repoId +
                ", repoName='" + repoName + '\'' +
                ", buyTime=" + buyTime +
                ", payTime=" + payTime +
                ", confirmTime=" + confirmTime +
                ", confirmUserId=" + confirmUserId +
                ", confirmUser='" + confirmUser + '\'' +
                ", printTime=" + printTime +
                ", printUserId=" + printUserId +
                ", printUser='" + printUser + '\'' +
                ", inspectionTime=" + inspectionTime +
                ", inspectionUserId=" + inspectionUserId +
                ", deliveryTime=" + deliveryTime +
                ", deliveryUserId=" + deliveryUserId +
                ", receiptTime=" + receiptTime +
                ", outPlatformType='" + outPlatformType + '\'' +
                ", outOrderNo='" + outOrderNo + '\'' +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", needInvoice=" + needInvoice +
                ", invoiceName='" + invoiceName + '\'' +
                ", invoiceContent='" + invoiceContent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", originalOrderId=" + originalOrderId +
                ", itemName='" + itemName + '\'' +
                ", postFee='" + postFee + '\'' +
                ", payment='" + payment + '\'' +
                ", expressInfoData=" + expressInfoData +
                ", orderItem=" + orderItem +
                ", itemCount=" + itemCount +
                ", itemNumCount=" + itemNumCount +
                '}';
    }
}
