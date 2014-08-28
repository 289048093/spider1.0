package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午9:25
 * 赠品规则-品牌
 */
public class GiftBrandItem {
    /**
     * id
     */
    private Integer id;
    /**
     * 优惠活动-品牌id
     */
    private Integer giftBrandId;
    /**
     * 赠送商品Id
     */
    private Integer giftProdId;
    /**
     * 赠送商品数量
     */
    private Integer giftProdCount;
    /**
     * 增加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除时间
     */
    private Date deleteTime;
    /**
     * 商品实体-赠品用
     */
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGiftBrandId() {
        return giftBrandId;
    }

    public void setGiftBrandId(Integer giftBrandId) {
        this.giftBrandId = giftBrandId;
    }

    public Integer getGiftProdId() {
        return giftProdId;
    }

    public void setGiftProdId(Integer giftProdId) {
        this.giftProdId = giftProdId;
    }

    public Integer getGiftProdCount() {
        return giftProdCount;
    }

    public void setGiftProdCount(Integer giftProdCount) {
        this.giftProdCount = giftProdCount;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "GiftBrandItem{" +
                "id=" + id +
                ", giftBrandId=" + giftBrandId +
                ", giftProdId=" + giftProdId +
                ", giftProdCount=" + giftProdCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", product=" + product +
                '}';
    }
}
