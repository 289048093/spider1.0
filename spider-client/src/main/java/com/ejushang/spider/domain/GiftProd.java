package com.ejushang.spider.domain;

import java.util.Date;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午9:57
 * 赠品规则-商品
 */
public class GiftProd {
    /**
     * id
     */
    private Integer id;
    /**
     * 销售商品id
     */
    private Integer sellProdId;
    /**
     * 赠品商品id
     */
    private Integer giftProdId;

    /**
     * 商品实体数组
     */
    private List<Product> productList;

    /**
     * 赠品商品数量
     */
    private Integer giftProdCount;
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

    @Override
    public String toString() {
        return "GiftProd{" +
                "id=" + id +
                ", sellProdId=" + sellProdId +
                ", giftProdId=" + giftProdId +
                ", productList=" + productList +
                ", giftProdCount=" + giftProdCount +
                ", inUse=" + inUse +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellProdId() {
        return sellProdId;
    }

    public void setSellProdId(Integer sellProdId) {
        this.sellProdId = sellProdId;
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
}
