package com.ejushang.spider.vo;

import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.util.Money;

import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午10:49
 * 商品
 */
public class ProductVo {
    private Integer id;
    private Integer prodId;
    /**
     * 品牌名
     */
    private String brandName;
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
     * 商品分类名
     */
    private String prodCategoryName;
    /**
     * 商品Id
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
     * 套餐价
     */
    private String mealPriceStr;
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
    /**
     * 分页开始下标
     */
    private int start;
    /**
     * 条数
     */
    private int limit;
    /**
     * 仓库信息
     */
    private Storage storage;

    /**
     * 套餐项默认个数
     *
     * @return
     */
    private int mealCount=1;

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public String getShopPriceStr() {
        return this.shopPriceStr;
    }

    public void setShopPriceStr(String shopPriceStr) {
        this.shopPriceStr = shopPriceStr;
    }
    public Long getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Long shopPrice) {
        this.setShopPriceStr(Money.CentToYuan(shopPrice).toString());
        this.shopPrice = shopPrice;
    }
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }


    public void setMealPrice(String mealPrice) {
        this.mealPriceStr = shopPriceStr;
    }

    public String getStandardPriceStr() {
        return this.standardPriceStr;
    }

    public void setStandardPriceStr(String standardPriceStr) {
        this.standardPriceStr = this.standardPriceStr;
    }

    public String getBuyPriceStr() {
        return this.buyPriceStr;
    }

    public void setBuyPriceStr(String buyPriceStr) {
        this.buyPriceStr = buyPriceStr;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProdCategoryName() {
        return prodCategoryName;
    }

    public void setProdCategoryName(String prodCategoryName) {
        this.prodCategoryName = prodCategoryName;
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

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.setBuyPriceStr(Money.CentToYuan(buyPrice).toString());
        this.buyPrice = buyPrice;
    }
    public String getMealPriceStr() {
        return shopPriceStr;
    }



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBoxSize() {

        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public String getSpeci() {
        return speci;
    }

    public void setSpeci(String speci) {
        this.speci = speci;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String toString() {
        return "ProductVo{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", brandId=" + brandId +
                ", prodName='" + prodName + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", prodCategoryName='" + prodCategoryName + '\'' +
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
                ", start=" + start +
                ", limit=" + limit +
                ", storage=" + storage +
                ", mealCount=" + mealCount +
                '}';
    }
}
