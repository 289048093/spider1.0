package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午11:21
 */
public class OriginalOrderItem {
    /** 主键编号 */
    private Integer id;
    /** 订单编号 */
    private String orderNo;
    /** 商品条形码(sku) */
    private String skuCode;
    /** 商品单价（单位：分） */
    private Long price;
    /** 商品数量 */
    private Long buyCount;
    /** 应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）（分） */
    private Long totalFee;
    /**
     * 子订单实付金额.对于多子订单的交易，计算公式如下：payment = price * num + adjust_fee - discount_fee ；
     * 单子订单交易，payment与主订单的payment一致，对于退款成功的子订单，由于主订单的优惠分摊金额，
     * 会造成该字段可能不为0.00元。建议使用退款前的实付金额减去退款单中的实际退款金额计算。
     * */
    private Long actualFee;
    /** 子订单级订单优惠金额 */
    private Long discountFee;
    /** 手工调整金额 */
    private Long adjustFee;
    /** 分摊之后的实付金额 */
    private Long divideOrderFee;
    /** 优惠分摊 */
    private Long partMjzDiscount;
    /** 记录创建时间 */
    private Date createTime;
    /** 记录更新时间 */
    private Date updateTime;
    /** 原始订单ID */
    private Integer orderId;
    /** 原始子订单编号 */
    private String subOrderNo;

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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public Long getActualFee() {
        return actualFee;
    }

    public void setActualFee(Long actualFee) {
        this.actualFee = actualFee;
    }

    public Long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    public Long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(Long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public Long getDivideOrderFee() {
        return divideOrderFee;
    }

    public void setDivideOrderFee(Long divideOrderFee) {
        this.divideOrderFee = divideOrderFee;
    }

    public Long getPartMjzDiscount() {
        return partMjzDiscount;
    }

    public void setPartMjzDiscount(Long partMjzDiscount) {
        this.partMjzDiscount = partMjzDiscount;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }


    public String toString() {
        return "OriginalOrderItem={" +
                "id = " + id + "," +
                "orderNo = " + orderNo + "," +
                "skuCode = " + skuCode + "," +
                "price = " + price + "," +
                "buyCount = " + buyCount + "," +
                "totalFee = " + totalFee + "," +
                "actualFee = " + actualFee + "," +
                "createTime = " + createTime + "," +
                "updateTime = " + updateTime + "," +
                "orderId = " + orderId + "," +
                "subOrderNo = " + subOrderNo + "," +
                "}";
    }
}
