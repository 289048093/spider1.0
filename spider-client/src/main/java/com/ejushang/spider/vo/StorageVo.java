package com.ejushang.spider.vo;

import com.ejushang.spider.domain.*;

import java.util.Date;

/**
 * User: tin
 * Date: 13-12-31
 * Time: 上午11:55
 */
public class StorageVo {
    /**
     * 库存编号
     */
    private String id;
    /**
     * 商品id
     */
    private String prodId;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 仓库id
     */
    private String repoId;
    /**
     * 仓库名
     */
    private String   repoName;
    /**
     * 实际库存
     */
    private Integer actuallyNumber;
    /**
     * 库存最后更新时间
     */
    private Date modifyTime;


    /**
     *  品牌
     */
    private String brandName;
    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 商品编号
     */
    private String prodNo;
    /**
     * 商品条形码
     */
    private String prodCode;

    /**
     * 销售价
     */
    private String shopPrice;

    /**
     * 市场价
     */
    private String standardPrice;

    /**
     * 进货价
     */
    private String buyPrice;
    /**
     * 产品描述
     */
    private String description;
    /**
     * 类别名称
     */
         private String prodCaName;
    /**
     * 类别ID
     */
    private String prodCaId;

    public String getProdCaId() {
        return prodCaId;
    }

    public void setProdCaId(String prodCaId) {
        this.prodCaId = prodCaId;
    }

    public String getProdCaName() {
        return prodCaName;
    }

    public void setProdCaName(String prodCaName) {
        this.prodCaName = prodCaName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public Integer getActuallyNumber() {
        return actuallyNumber;
    }

    public void setActuallyNumber(Integer actuallyNumber) {
        this.actuallyNumber = actuallyNumber;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
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

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(String standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StorageVo{" +
                "id='" + id + '\'' +
                ", prodId='" + prodId + '\'' +
                ", prodName='" + prodName + '\'' +
                ", repoId='" + repoId + '\'' +
                ", repoName='" + repoName + '\'' +
                ", actuallyNumber=" + actuallyNumber +
                ", modifyTime=" + modifyTime +
                ", brandName='" + brandName + '\'' +
                ", brandId='" + brandId + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", shopPrice='" + shopPrice + '\'' +
                ", standardPrice='" + standardPrice + '\'' +
                ", buyPrice='" + buyPrice + '\'' +
                ", description='" + description + '\'' +
                ", prodCaName='" + prodCaName + '\'' +
                ", prodCaId='" + prodCaId + '\'' +
                '}';
    }
}
