package com.ejushang.spider.domain;

import com.ejushang.spider.util.Money;

import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:28
 * 套餐项表
 */
public class MealsetItem {
    private Integer id;
    /**
     *      套餐id
     */
    private Integer mealId;
    /**
     * 商品id
     */
    private Integer prodId;
    /**
     * 套餐价
     */
    private Long mealPrice;
    private String mealPriceStr;
    /**
     * 套餐中的数量
     */
    private Integer mealCount;

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

    public String getMealPriceStr() {
        return mealPriceStr;
    }

    public void setMealPriceStr(String mealPriceStr) {
        this.mealPrice= Money.YuanToCent(mealPriceStr);
        this.mealPriceStr = mealPriceStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public Long getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(Long mealPrice) {
        this.mealPriceStr=Money.CentToYuan(mealPrice).toString();
        this.mealPrice = mealPrice;
    }

    public Integer getMealCount() {
        return mealCount;
    }

    public void setMealCount(Integer mealCount) {
        this.mealCount = mealCount;
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


    @Override
    public String toString() {
        return "MealsetItem{" +
                "id=" + id +
                ", mealId=" + mealId +
                ", prodId=" + prodId +
                ", mealPrice=" + mealPrice +
                ", mealPriceStr='" + mealPriceStr + '\'' +
                ", mealCount=" + mealCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }
}
