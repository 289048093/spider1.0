package com.ejushang.spider.domain;

import com.ejushang.spider.constant.OrderSplitStatus;
import com.ejushang.spider.constant.OrderStatus;

import java.util.Date;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午10:58
 */
public class Order {

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
     * 商品金额（商品价格乘以数量的总金额）
     */
    private Long totalFee;
    /** 建议使用trade.promotion_details查询系统优惠 系统优惠金额（如打折，VIP，满就送等） */
    private Long discountFee;
    /** 实付金额 */
    private Long payment;
    /** 邮费 */
    private Long postFee;
    /** 手工调整金额 */
    private Long adjustFee;

    //下面的属性未参与计算,其值一直等于抓取下来的原始订单中的金额(如果手动下单则为null)
    /** 是否包含邮费 */
    private Boolean hasPostFee;
    /** 买家使用积分,下单时生成，且一直不变 */
    private Long pointFee;
    /** 交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零） */
    private Long availableConfirmFee;
    /** 买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0 */
    private Long realPointFee;
    /** 卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额） */
    private Long receivedPayment;
    //end


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
     * 拆分状态
     */
    private String splitStatus;

    /**
     * 订单详细实体
     */
    private List<OrderItem> orderItemList;

    /**
     * 仓库表实体
     */
    private Repository repository;

    public String getSplitStatus() {
        return splitStatus;
    }

    public void setSplitStatus(String splitStatus) {
        this.splitStatus = splitStatus;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
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


    public List<OrderItem> getOrderItemList() {
        return orderItemList;
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
        if (orderStatus.equals("pending")) {
            orderStatus = OrderStatus.WAIT_PROCESS.toString();


        }
        if (orderStatus.equals("hasImport")) {
            orderStatus = OrderStatus.CONFIRMED.toString();

        }
        if (orderStatus.equals("hasPrint")) {
            orderStatus = OrderStatus.PRINTED.toString();

        }
        if (orderStatus.equals("hasCheck")) {
            orderStatus = OrderStatus.EXAMINED.toString();

        }
        if (orderStatus.equals("hasDone")) {
            orderStatus = OrderStatus.INVOICED.toString();

        }
        if (orderStatus.equals("null") || orderStatus.equals(null)) {
            orderStatus = null;
        }

        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "Order={" +
                "id = " + id + "," +
                "orderNo = " + orderNo + "," +
                "orderType = " + orderType + "," +
                "orderStatus = " + orderStatus + "," +
                "totalFee = " + totalFee + "," +
                "buyerId = " + buyerId + "," +
                "buyerMessage = " + buyerMessage + "," +
                "remark = " + remark + "," +
                "receiverName = " + receiverName + "," +
                "receiverPhone = " + receiverPhone + "," +
                "receiverMobile = " + receiverMobile + "," +
                "receiverZip = " + receiverZip + "," +
                "receiverState = " + receiverState + "," +
                "receiverCity = " + receiverCity + "," +
                "receiverDistrict = " + receiverDistrict + "," +
                "receiverAddress = " + receiverAddress + "," +
                "shippingNo = " + shippingNo + "," +
                "shippingComp = " + shippingComp + "," +
                "repoId = " + repoId + "," +
                "repoName = " + repoName + "," +
                "buyTime = " + buyTime + "," +
                "payTime = " + payTime + "," +
                "confirmTime = " + confirmTime + "," +
                "confirmUserId = " + confirmUserId + "," +
                "confirmUser = " + confirmUser + "," +
                "printTime = " + printTime + "," +
                "printUserId = " + printUserId + "," +
                "printUser = " + printUser + "," +
                "inspectionTime = " + inspectionTime + "," +
                "inspectionUserId = " + inspectionUserId + "," +
                "deliveryTime = " + deliveryTime + "," +
                "deliveryUserId = " + deliveryUserId + "," +
                "receiptTime = " + receiptTime + "," +
                "outPlatformType = " + outPlatformType + "," +
                "outOrderNo = " + outOrderNo + "," +
                "shopId = " + shopId + "," +
                "shopName = " + shopName + "," +
                "needInvoice = " + needInvoice + "," +
                "invoiceName = " + invoiceName + "," +
                "invoiceContent = " + invoiceContent + "," +
                "createTime = " + createTime + "," +
                "updateTime = " + updateTime + "," +
                "originalOrderId = " + originalOrderId + "," +
                "splitStatus = " + splitStatus + "," +
                "}";
    }

    public Integer getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(Integer originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    /**
     * 计算订单中的金额
     *
     * @return
     */
    public void calPayment() {

        //防御性写法,累不爱.
        long totalFee = 0L;
        long actualFee = 0L;
        long discountFee = 0L;
        long adjustFee = 0L;
        long postFee = 0L;

        if (orderItemList != null) {
            for (OrderItem orderItem : orderItemList) {
                long itemActualFee = orderItem.getActualFee() == null ? 0 : orderItem.getActualFee();
                long itemTotalFee = orderItem.getTotalFee() == null ? 0 : orderItem.getTotalFee();
                long itemDiscountFee = orderItem.getDiscountFee() == null ? 0 : orderItem.getDiscountFee();
                long itemAdjustFee = orderItem.getAdjustFee() == null ? 0 : orderItem.getAdjustFee();
                long itemPostFee = orderItem.getPostFee() == null ? 0 : orderItem.getPostFee();

                actualFee += itemActualFee;
                totalFee += itemTotalFee;
                discountFee += itemDiscountFee;
                adjustFee += itemAdjustFee;
                postFee += itemPostFee;
            }

            this.totalFee = totalFee;
            this.payment = actualFee;
            this.discountFee = discountFee;
            this.adjustFee = adjustFee;
            this.postFee = postFee;
        }

    }


}
