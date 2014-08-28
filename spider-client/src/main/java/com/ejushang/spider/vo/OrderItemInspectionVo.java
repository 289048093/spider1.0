package com.ejushang.spider.vo;

/**
 * User: Blomer
 * Date: 14-1-13
 * Time: 上午11:43
 */
public class OrderItemInspectionVo {
    /**
     * 条形码
     */
    private String skuCode;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 商品编号
     */
    private String prodCode;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 商品单价
     */
    private Long prodPrice;
    /**
     * 购买数量
     */
    private Integer prodCount;
    /**
     * 实际库存
     */
    private Integer actuallyNumber;
    /**
     * 商品名称
     */
    private String brandName;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getActuallyNumber() {
        return actuallyNumber;
    }

    public void setActuallyNumber(Integer actuallyNumber) {
        this.actuallyNumber = actuallyNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
