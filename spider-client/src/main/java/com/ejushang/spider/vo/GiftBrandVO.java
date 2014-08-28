package com.ejushang.spider.vo;

/**
 * User: Blomer
 * Date: 14-1-2
 * Time: 下午3:53
 */
public class GiftBrandVO {
    /**
     * id
     */
    private Integer id;
    /**
     *  赠品-品牌id
     */
    private Integer brandId;

    private String brandName;
    /**
     * 开始价格
     */
    private Long priceBegin;
    /**
     * 结束价格
     */
    private Long priceEnd;
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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
}
