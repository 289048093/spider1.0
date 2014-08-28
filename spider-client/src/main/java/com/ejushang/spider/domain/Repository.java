package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 上午10:58
 */
public class Repository {
    /**
     * 仓库表id
     */
    private Integer id;
    /**
     * 仓库名
     */
    private String name;
    /**
     * 仓库编码
     */
    private String repoCode;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 仓库负责人
     */
    private String chargePerson;
    /**
     * 责任人id
     */
    private Integer chargePersonId;
    /**
     * 责任人电话
     */
    private String chargeMobile;
    /**
     * 负责人手机号
     */
    private String chargePhone;
    /**
     * 创建仓库时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除时间
     */
    private Date deleteTime;
    /**
     * 物流公司
     */
    private String shippingComp;

    /**
     * 删除状态
     */
//    private Boolean delete=false;

    public String getShippingComp() {
        return shippingComp;
    }
    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
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

    public String getRepoCode() {
        return repoCode;
    }

    public void setRepoCode(String repoCode) {
        this.repoCode = repoCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public Integer getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(Integer chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public String getChargeMobile() {
        return chargeMobile;
    }

    public void setChargeMobile(String chargeMobile) {
        this.chargeMobile = chargeMobile;
    }

    public String getChargePhone() {
        return chargePhone;
    }

    public void setChargePhone(String chargePhone) {
        this.chargePhone = chargePhone;
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

//    public Boolean getDelete() {
//        return delete;
//    }
//
//    public void setDelete(Boolean delete) {
//        this.delete = delete;
//    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", repoCode='" + repoCode + '\'' +
                ", address='" + address + '\'' +
                ", chargePerson='" + chargePerson + '\'' +
                ", chargePersonId=" + chargePersonId +
                ", chargeMobile='" + chargeMobile + '\'' +
                ", chargePhone='" + chargePhone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }
}