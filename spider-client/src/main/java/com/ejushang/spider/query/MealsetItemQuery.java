package com.ejushang.spider.query;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:28
 * 套餐项表  query实体类
 */
public class MealsetItemQuery {
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
    /**
     * 套餐中的数量
     */
    private Integer mealCount;

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
        this.mealPrice = mealPrice;
    }

    public Integer getMealCount() {
        return mealCount;
    }

    public void setMealCount(Integer mealCount) {
        this.mealCount = mealCount;
    }



    @Override
    public String toString() {
        return "MealsetItemQuery{" +
                "id=" + id +
                ", mealId=" + mealId +
                ", prodId=" + prodId +
                ", mealPrice=" + mealPrice +
                ", mealCount=" + mealCount +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealsetItemQuery that = (MealsetItemQuery) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mealCount != null ? !mealCount.equals(that.mealCount) : that.mealCount != null) return false;
        if (mealId != null ? !mealId.equals(that.mealId) : that.mealId != null) return false;
        if (mealPrice != null ? !mealPrice.equals(that.mealPrice) : that.mealPrice != null) return false;
        if (prodId != null ? !prodId.equals(that.prodId) : that.prodId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mealId != null ? mealId.hashCode() : 0);
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (mealPrice != null ? mealPrice.hashCode() : 0);
        result = 31 * result + (mealCount != null ? mealCount.hashCode() : 0);
        return result;
    }
}
