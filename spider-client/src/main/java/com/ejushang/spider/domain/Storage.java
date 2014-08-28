package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 上午11:09
 */
public class Storage {
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
     * 实际库存
     */
    private Integer actuallyNumber;
    /**
     * 库存最后更新时间
     */
    private Date modifyTime;
    /**
     * 产品实体
     */
    private Product product;
    /**
     * 仓库实体
     */
    private Repository repository;

    /**
     * 品牌实体
     */
    private Brand brand;
    /**
     * 品牌类别
     */
    private ProdCategory prodCategory;


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProdCategory getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(ProdCategory prodCategory) {
        this.prodCategory = prodCategory;
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

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", prodId=" + prodId +
                ", repoId=" + repoId +
                ", actuallyNumber=" + actuallyNumber +
                ", modifyTime=" + modifyTime +
                ", product=" + product +
                ", repository=" + repository +
                ", brand=" + brand +
                ", prodCategory=" + prodCategory +

                '}';
    }
    public StorageLog addLog(String type,Storage afterStorage,User user){
        StorageLog storageLog=new StorageLog();
        storageLog.setBeforeNum(this.actuallyNumber);
        storageLog.setAfterNum(afterStorage.getActuallyNumber());
        storageLog.setRepoId(this.repoId);
        storageLog.setProdId(this.prodId);
        storageLog.setType(type);
        if(user!=null){
            storageLog.setOperatorId(user.getId());
            storageLog.setOperator(user.getUsername());
        }
        return storageLog;
    }
}
