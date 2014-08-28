package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: tin
 * Date: 14-4-10
 * Time: 下午5:20
 */
public class StorageLog {

    private Integer id;
    private String operator;
    private Integer beforeNum;
    private Integer afterNum;
    private Date modifyTime;
    private Integer operatorId;
    private Integer prodId;
    private Integer repoId;
    private String type;

    @Override
    public String toString() {
        return "StorageLog{" +
                "id=" + id +
                ", operator='" + operator + '\'' +
                ", beforeNum=" + beforeNum +
                ", afterNum=" + afterNum +
                ", modifyTime=" + modifyTime +
                ", operatorId=" + operatorId +
                ", prodId=" + prodId +
                ", repoId=" + repoId +
                ", type=" + type +
                '}';
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getBeforeNum() {
        return beforeNum;
    }

    public void setBeforeNum(Integer beforeNum) {
        this.beforeNum = beforeNum;
    }

    public Integer getAfterNum() {
        return afterNum;
    }

    public void setAfterNum(Integer afterNum) {
        this.afterNum = afterNum;
    }



    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
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
}
