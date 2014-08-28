package com.ejushang.spider.domain;

import java.util.Date;
import java.util.List;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:36
 * 套餐表
 */
public class Mealset {

    private Integer id;
    /**
     * 套餐名
     */
    private String name;
    /**
     * 套餐条形码
     */
    private String code;
    /**
     * 卖点描述
     */
    private String sellDescription;

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
     * 套餐下的套餐项
     */
    private List<MealsetItem> mealsetItemList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSellDescription() {
        return sellDescription;
    }

    public void setSellDescription(String sellDescription) {
        this.sellDescription = sellDescription;
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

    public List<MealsetItem> getMealsetItemList() {
        return mealsetItemList;
    }

    public void setMealsetItemList(List<MealsetItem> mealsetItemList) {
        this.mealsetItemList = mealsetItemList;
    }

    @Override
    public String toString() {
        return "Mealset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sellDescription='" + sellDescription + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", mealsetItemList=" + mealsetItemList +
                '}';
    }

    /**
     * 计算套餐总价
     * @return
     */
    public long calTotalPrice() {
        long total = 0L;
        if(mealsetItemList != null) {
            for(MealsetItem mealsetItem : mealsetItemList) {
                total += mealsetItem.getMealPrice() * mealsetItem.getMealCount();
            }
        }
        return total;
    }
}
