package com.ejushang.spider.domain;

import java.util.Date;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午9:25
 * 赠品规则-品牌
 */
public class GiftBrand {
    /**
     * id
     */
    private Integer id;
    /**
     * 赠品-品牌id
     */
    private Integer brandId;

    /**
     * 品牌实体
     */
    private Brand brand;

    /**
     * 开始价格
     */
    private Long priceBegin;
    /**
     * 结束价格
     */
    private Long priceEnd;

    /**
     * 商品实体
     */
    private Product product;

    /**
     * 是否在使用
     */
    private Boolean inUse;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 优惠活动-品牌 下的赠送的商品项
     */
    private List<GiftBrandItem> giftBrandItemList;

    @Override
    public String toString() {
        return "GiftBrand{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", brand=" + brand +
                ", priceBegin=" + priceBegin +
                ", priceEnd=" + priceEnd +
                ", product=" + product +
                ", inUse=" + inUse +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", giftBrandItemList=" + giftBrandItemList +
                '}';
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

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


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public List<GiftBrandItem> getGiftBrandItemList() {
        return giftBrandItemList;
    }

    public void setGiftBrandItemList(List<GiftBrandItem> giftBrandItemList) {
        this.giftBrandItemList = giftBrandItemList;
    }
}
