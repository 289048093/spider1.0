package com.ejushang.spider.query;

import com.ejushang.spider.util.Page;

import java.util.Date;

/**
 * User: tin
 * Date: 14-1-3
 * Time: 上午9:23
 */
public class StorageQuery extends BaseQuery {
    /**
     * 库存编号
     */
    private Integer id;
    /**
     * 商品id
     */
    private Integer prodId;
    /**
     * 仓库id
     */
    private Integer repoId;


    /**
     * 品牌ID
     */
    private Integer brandId;
    /**
     * 类别ID
     */
    private Integer cid;
    /**
     * 商品编码
     */
    private String prodNo;
    /**
     * 商品条形码
     */
    private String prodCode;
    /**
     * 商品名称
     */
    private String prodName;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }
    @Override
    public String toString() {
        return "StorageQuery{" +
                "id=" + id +
                ", prodId=" + prodId +
                ", repoId=" + repoId +
                ", brandId=" + brandId +
                ", cid=" + cid +
                ", prodNo='" + prodNo + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", prodName='" + prodName + '\'' +
                '}';
    }

}
