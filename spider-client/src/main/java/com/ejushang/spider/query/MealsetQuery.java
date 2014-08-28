package com.ejushang.spider.query;

import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:36
 * 套餐表      query实体类
 */
public class MealsetQuery  extends BaseQuery {
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
    public String toString() {
        return "MealsetQuery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sellDescription='" + sellDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealsetQuery mealset = (MealsetQuery) o;

        if (id != null ? !id.equals(mealset.id) : mealset.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (sellDescription != null ? sellDescription.hashCode() : 0);
        return result;
    }
}
