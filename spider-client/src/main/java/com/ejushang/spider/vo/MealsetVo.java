package com.ejushang.spider.vo;

import com.ejushang.spider.domain.MealsetItem;

import java.util.Date;
import java.util.List;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:36
 * 套餐表      query实体类
 */
public class MealsetVo {
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
    private List<MealsetItemVo> mealsetItemVoList;

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

    public List<MealsetItemVo> getMealsetItemVoList() {
        return mealsetItemVoList;
    }

    public void setMealsetItemVoList(List<MealsetItemVo> mealsetItemVoList) {
        this.mealsetItemVoList = mealsetItemVoList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealsetVo mealsetVo = (MealsetVo) o;

        if (id != null ? !id.equals(mealsetVo.id) : mealsetVo.id != null) return false;
        return true;
    }


    @Override
    public String toString() {
        return "MealsetVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sellDescription='" + sellDescription + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", mealsetItemVoList=" + mealsetItemVoList +
                '}';
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (sellDescription != null ? sellDescription.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        result = 31 * result + (mealsetItemVoList != null ? mealsetItemVoList.hashCode() : 0);
        return result;
    }
}
