package com.ejushang.spider.vo;

/**
 * User: Blomer
 * Date: 14-1-2
 * Time: 下午3:53
 */
public class GiftBrandItemVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 品牌
     */
    private String brandName;
    /**
     * 商品id
     */
    private Integer prodId;
    /**
     * 商品编号
     */
    private String prodNo;
    /**
     * 商品条形码
     */
    private String prodCode;

    /**
     * 商品名字
     */
    private String ProdName;
    /**
     * 赠送数量
     */
    private Integer giftProdCount;

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

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
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
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public Integer getGiftProdCount() {
        return giftProdCount;
    }

    public void setGiftProdCount(Integer giftProdCount) {
        this.giftProdCount = giftProdCount;
    }
}
