package com.ejushang.spider.query;

/**
 * User: Blomer
 * Date: 14-01-02
 * Time: 上午9:25
 * 赠品规则-品牌
 */
public class GiftBrandQuery extends BaseQuery{
    /**
     * id
     */
    private Integer id;
    /**
     *  赠品-品牌id
     */
    private Integer brandId;
    /**
     * 叫价
     */
    private Long priceBegin;
    /**
     * 实价
     */
    private Long priceEnd;
    /**
     * 是否在使用
     */
    private Boolean inUse;

    /**
     * 商品名称
     */
    private String brandName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Long getPriceBegin() {
        return priceBegin;
    }

    public void setPriceBegin(Long priceBegin) {
        this.priceBegin = priceBegin;
    }

    public Long getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Long priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "GiftBrandQuery{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", priceBegin=" + priceBegin +
                ", priceEnd=" + priceEnd +
                ", inUse=" + inUse +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
