package com.ejushang.spider.vo;

import com.ejushang.spider.domain.Product;

import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:28
 * 套餐项表  query实体类
 */
public class MealsetItemVo {
    private Integer id;
    /**
     * 套餐id
     */
    private Integer mealId;
    /**
     * 商品id
     */
    private Integer prodId;
    /**
     * 商品名
     */
    private String prodName;
    /**
     * 商品条形码
     */
    private String prodCode;
    /**
     * 编号
     */
    private String prodNo;
    /**
     * 商品
     */
    private Product product;

    /**
     * 套餐价
     */
    private Long mealPrice;
    private String mealPriceStr;
    /**
     * 销售价
     */
    private String shopPriceStr;

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

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopPriceStr() {
        return shopPriceStr;
    }

    public void setShopPriceStr(String shopPriceStr) {
        this.shopPriceStr=shopPriceStr;
    }

    public String getMealPriceStr() {
        return mealPriceStr;
    }

    public void setMealPriceStr(String mealPriceStr) {
        this.mealPriceStr = mealPriceStr;
    }

    public Integer getMealId() {
        return mealId;
    }

    public Long getMealPrice() {

        return mealPrice;
    }

    public void setMealPrice(Long mealPrice) {
        this.mealPrice = mealPrice;
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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    @Override
    public String toString() {
        return "MealsetItemVo{" +
                "id=" + id +
                ", mealId=" + mealId +
                ", prodId=" + prodId +
                ", prodName='" + prodName + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", product=" + product +
                ", mealPrice=" + mealPrice +
                ", mealPriceStr='" + mealPriceStr + '\'' +
                ", mealCount=" + mealCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealsetItemVo that = (MealsetItemVo) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mealId != null ? mealId.hashCode() : 0);
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (prodName != null ? prodName.hashCode() : 0);
        result = 31 * result + (prodCode != null ? prodCode.hashCode() : 0);
        result = 31 * result + (prodNo != null ? prodNo.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (mealPrice != null ? mealPrice.hashCode() : 0);
        result = 31 * result + (mealPriceStr != null ? mealPriceStr.hashCode() : 0);
        result = 31 * result + (mealCount != null ? mealCount.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        return result;
    }
}
