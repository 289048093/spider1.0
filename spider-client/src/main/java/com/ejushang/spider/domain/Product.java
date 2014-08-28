package com.ejushang.spider.domain;

import com.ejushang.spider.util.Money;

import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午10:49
 * 商品
 */
public class Product {
    private Integer id;
    /**
     * 商品表实体
     */
    private Brand brand;
    /**
     * 库存表实体
     */
    private Storage storage;
    /**
     * 分类表实体
     */
    private ProdCategory prodCategory;
    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 商品名
     */
    private String prodName;
    /**
     * 商品编号
     */
    private String prodNo;
    /**
     * 商品条形码
     */
    private String prodCode;
    /**
     * 商品分类id
     */
    private Integer cid;
    /**
     * 产品描述
     */
    private String description;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 销售价
     */
    private Long shopPrice;
    private String shopPriceStr;
    /**
     * 市场价
     */
    private Long standardPrice;
    private String standardPriceStr;

    /**
     * 进货价
     */
    private Long buyPrice;
    private String buyPriceStr;
    /**
     * 颜色
     */
    private String color;
    /**
     * 重量
     */
    private String weight;
    /**
     * 包装尺寸
     */
    private String boxSize;
    /**
     * 规格
     */
    private String speci;
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
     * 赠品/商品 类型
     */
    private String type;

    public String getShopPriceStr() {
        return shopPriceStr;
    }

    public void setShopPriceStr(String shopPriceStr) {
        this.shopPrice=Money.YuanToCent(shopPriceStr);
        this.shopPriceStr = shopPriceStr;
    }

    public String getStandardPriceStr() {
        return standardPriceStr;
    }

    public void setStandardPriceStr(String standardPriceStr) {
        this.standardPrice=Money.YuanToCent(standardPriceStr);
        this.standardPriceStr = standardPriceStr;
    }

    public String getBuyPriceStr() {
        return buyPriceStr;
    }

    public void setBuyPriceStr(String buyPriceStr) {
        this.buyPrice=Money.YuanToCent(buyPriceStr);
        this.buyPriceStr = buyPriceStr;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public ProdCategory getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(ProdCategory prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPriceStr=Money.CentToYuan(buyPrice).toString();
        this.buyPrice = buyPrice;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpeci() {
        return speci;
    }

    public void setSpeci(String speci) {
        this.speci = speci;
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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Long getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Long shopPrice) {
        this.shopPriceStr=Money.CentToYuan(shopPrice).toString();
        this.shopPrice = shopPrice;
    }

    public Long getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Long standardPrice) {
        this.standardPriceStr=Money.CentToYuan(standardPrice).toString();
        this.standardPrice = standardPrice;
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

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", brand=" + brand +
                ", storage=" + storage +
                ", prodCategory=" + prodCategory +
                ", brandId=" + brandId +
                ", prodName='" + prodName + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", cid=" + cid +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", shopPrice=" + shopPrice +
                ", shopPriceStr='" + shopPriceStr + '\'' +
                ", standardPrice=" + standardPrice +
                ", standardPriceStr='" + standardPriceStr + '\'' +
                ", buyPrice=" + buyPrice +
                ", buyPriceStr='" + buyPriceStr + '\'' +
                ", color='" + color + '\'' +
                ", weight='" + weight + '\'' +
                ", boxSize='" + boxSize + '\'' +
                ", speci='" + speci + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", type='" + type + '\'' +
                '}';
    }
}
