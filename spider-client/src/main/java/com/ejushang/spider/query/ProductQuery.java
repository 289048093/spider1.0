package com.ejushang.spider.query;


import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午10:49
 * 商品
 */
public class ProductQuery extends BaseQuery {
    private Integer id;
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
    /**
     * 市场价
     */
    private Long standardPrice;
    /**
     * 卖点描述
     */
    private String sellDescription;
    /**
     * 进货价
     */
    private Long buyPrice;
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
     * 搜索 类型
     */
    private String searchType;
    /**
     * 搜索 类型
     */
    private String searchValue;
    /**
     * 赠品/商品 类型
     */
    private String type;


    public String getType() {
        return type;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
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
        this.shopPrice = shopPrice;
    }

    public Long getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Long standardPrice) {
        this.standardPrice = standardPrice;
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

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSpeci() {
        return speci;
    }

    public void setSpeci(String speci) {
        this.speci = speci;
    }


    @Override
    public String toString() {
        return "ProductQuery{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", prodName='" + prodName + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", cid=" + cid +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", shopPrice=" + shopPrice +
                ", standardPrice=" + standardPrice +
                ", sellDescription='" + sellDescription + '\'' +
                ", buyPrice=" + buyPrice +
                ", color='" + color + '\'' +
                ", weight='" + weight + '\'' +
                ", boxSize='" + boxSize + '\'' +
                ", speci='" + speci + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", searchType='" + searchType + '\'' +
                ", searchValue='" + searchValue + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductQuery that = (ProductQuery) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (prodName != null ? prodName.hashCode() : 0);
        result = 31 * result + (prodNo != null ? prodNo.hashCode() : 0);
        result = 31 * result + (prodCode != null ? prodCode.hashCode() : 0);
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (shopPrice != null ? shopPrice.hashCode() : 0);
        result = 31 * result + (standardPrice != null ? standardPrice.hashCode() : 0);
        result = 31 * result + (sellDescription != null ? sellDescription.hashCode() : 0);
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (boxSize != null ? boxSize.hashCode() : 0);
        result = 31 * result + (speci != null ? speci.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        result = 31 * result + (searchType != null ? searchType.hashCode() : 0);
        result = 31 * result + (searchValue != null ? searchValue.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
