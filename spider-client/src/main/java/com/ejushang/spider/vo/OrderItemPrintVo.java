package com.ejushang.spider.vo;

/**
 * User: Blomer
 * Date: 14-1-10
 * Time: 下午4:15
 */
public class OrderItemPrintVo {
    /**
     * 订单详细ID
     */
    private Integer id;
    /**
     * 商品编码
     */
    private String prodCode;
    /**
     * 商品名
     */
    private String prodName;
    /**
     * 颜色
     */
    private String color;
    /**
     * 规格
     */
    private String speci;
    /**
     * 单价
     */
    private  String  prodPrice;
    /**
     * 购买数量
     */
    private Integer prodCount;
    /**
     * 总金额
     */
    private String totalFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSpeci() {
        return speci;
    }

    public void setSpeci(String speci) {
        this.speci = speci;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
}
