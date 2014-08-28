package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午11:21
 */
public class OrderItem {
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
    private Long prodPrice;
    /** 商品数量 */
    private Integer prodCount;
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
    /** 邮费 */
    private Long postFee;


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
    /** 外部平台子订单ID */
    private String subOutOrderNo;

    /**
     * 不需要写入数据的三个属性
     */
    /** 商品属性*/
    private String sellPropsStr;
    /** 理论库存*/
    private Integer theoryNumber;
    /** 品牌名*/
    private String brandName;


    /**
     * Order实体
     */
    private Order order;



    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Long getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Long prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Integer getProdCount() {
        return prodCount;
    }

    public void setProdCount(Integer prodCount) {
        this.prodCount = prodCount;
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

    public Long getPostFee() {
        return postFee;
    }

    public void setPostFee(Long postFee) {
        this.postFee = postFee;
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

    public String getSellPropsStr() {
        return sellPropsStr;
    }

    public void setSellPropsStr(String sellPropsStr) {
        this.sellPropsStr = sellPropsStr;
    }

    public Integer getTheoryNumber() {
        return theoryNumber;
    }

    public void setTheoryNumber(Integer theoryNumber) {
        this.theoryNumber = theoryNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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


    public String getSubOutOrderNo() {
        return subOutOrderNo;
    }

    public void setSubOutOrderNo(String subOutOrderNo) {
        this.subOutOrderNo = subOutOrderNo;
    }


    public String toString() {
        return "OrderItem={" +
                "id = " + id + "," +
                "orderNo = " + orderNo + "," +
                "prodId = " + prodId + "," +
                "prodCode = " + prodCode + "," +
                "skuCode = " + skuCode + "," +
                "prodName = " + prodName + "," +
                "prodPrice = " + prodPrice + "," +
                "prodCount = " + prodCount + "," +
                "totalFee = " + totalFee + "," +
                "actualFee = " + actualFee + "," +
                "outOrderNo = " + outOrderNo + "," +
                "itemType = " + itemType + "," +
                "priceDescription = " + priceDescription + "," +
                "createTime = " + createTime + "," +
                "updateTime = " + updateTime + "," +
                "orderId = " + orderId + "," +
                "subOutOrderNo = " + subOutOrderNo + "," +
                "}";
    }
}
