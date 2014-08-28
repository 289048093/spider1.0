package com.ejushang.spider.vo;

/**
 * User: Blomer
 * Date: 14-1-5
 * Time: 下午9:12
 * To change this template use File | Settings | File Templates.
 */
public class GiftProdVO {
    /**
     * id
     */
    private Integer id;

    private String brandName;
    /**
     * 销售商品id
     */
    private Integer sellProdId;
    /**
     * 销售商品名称
     */
    private String sellProdName;

    /**
     * 商品编号
     */
    private String prodNo;

    /**
     * 商品条形码
     */
    private String prodCode;
    /**
     * 赠品商品id
     */
    private Integer giftProdId;

    /**
     * 赠品商品名称
     */
    private String giftProdName;

    /**
     * 赠品商品数量
     */
    private Integer giftProdCount;
    /**
     * 是否在使用
     */
    private Boolean inUse;

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

    public Integer getSellProdId() {
        return sellProdId;
    }

    public void setSellProdId(Integer sellProdId) {
        this.sellProdId = sellProdId;
    }

    public String getSellProdName() {
        return sellProdName;
    }

    public void setSellProdName(String sellProdName) {
        this.sellProdName = sellProdName;
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

    public Integer getGiftProdId() {
        return giftProdId;
    }

    public void setGiftProdId(Integer giftProdId) {
        this.giftProdId = giftProdId;
    }

    public String getGiftProdName() {
        return giftProdName;
    }

    public void setGiftProdName(String giftProdName) {
        this.giftProdName = giftProdName;
    }

    public Integer getGiftProdCount() {
        return giftProdCount;
    }

    public void setGiftProdCount(Integer giftProdCount) {
        this.giftProdCount = giftProdCount;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }
}
