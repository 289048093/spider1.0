package com.ejushang.spider.vo;

/**
 * User: Blomer
 * Date: 14-1-7
 * Time: 下午4:38
 */
public class ProdVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 品牌名
     */
    private String brandName;
    /**
     * 商品编号
     */
    private String prodNo;
    /**
     * 商品条形码
     */
    private String prodCode;
    /**
     * 商品名
     */
    private String prodName;
    /**
     * 销售价
     */
    private Long shopPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
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

    public Long getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Long shopPrice) {
        this.shopPrice = shopPrice;
    }

    @Override
    public String toString() {
        return "ProdVo{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", prodName='" + prodName + '\'' +
                ", shopPrice=" + shopPrice +
                '}';
    }
}
