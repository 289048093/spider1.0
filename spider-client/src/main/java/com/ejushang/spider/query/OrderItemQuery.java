package com.ejushang.spider.query;

import com.ejushang.spider.domain.Product;

import java.util.Date;

/**
 * User: tin
 * Date: 14-1-18
 * Time: 上午10:53
 */
public class OrderItemQuery {
    /** 主键编号 */
    private Integer id;
    /** 订单编号 */
    private String orderNo;
    /** 商品id */
    private Integer prodId;
    /** 商品编码(商品） */
    private String prodCode;
    /** 商品条形码(sku) */
    private String skuCode;
    /** 商口名称 */
    private String prodName;
    /** 商品单价（单位：分） */
    private String prodPrice;
    /** 商品数量 */
    private Integer prodCount;
    /** 应付总金额（分） */
    private String totalFee;
    /** 实付总金额（分） */
    private String actualFee;
    /** 外部平台的原始订单编号 */
    private String outOrderNo;
    /** 订单项类型（商品，套餐） */
    private String itemType;
    /** 价格描述 */
    private String priceDescription;
    /** 记录创建时间 */
    private Date createTime;
    /** 记录更新时间 */
    private Date updateTime;
    /** 订单ID */
    private Integer orderId;
    /** 商品id */
    private Product prod;
    /* 邮费*/
    private String postFee;

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
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

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Integer getProdCount() {
        return prodCount;
    }

    public void setProdCount(Integer prodCount) {
        this.prodCount = prodCount;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getActualFee() {
        return actualFee;
    }

    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
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

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    @Override
    public String toString() {
        return "OrderItemQuery{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", prodId=" + prodId +
                ", prodCode='" + prodCode + '\'' +
                ", skuCode='" + skuCode + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodPrice='" + prodPrice + '\'' +
                ", prodCount=" + prodCount +
                ", totalFee='" + totalFee + '\'' +
                ", actualFee='" + actualFee + '\'' +
                ", outOrderNo='" + outOrderNo + '\'' +
                ", itemType='" + itemType + '\'' +
                ", priceDescription='" + priceDescription + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", orderId=" + orderId +
                ", prod=" + prod +
                ", postFee='" + postFee + '\'' +
                '}';
    }
}
