package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 物流设计实体类
 */


public class Delivery {
    private Integer id;
    /**
     * 物流公司名
     */

    private String name;
    /**
     * 物流单号递增规律
     */

    private Integer law;
    /**
     * 物流信息打印的lodop代码
     */

    private String printHtml;
    /**
     * 物流设计图片
     */
    private String logisticsPicturePath;
    /**
     * 创建时间
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

    public Delivery(Integer id) {
        this.id = id;
    }
    public Delivery() {
        this.id = id;
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

    public Integer getLaw() {
        return law;
    }

    public void setLaw(Integer law) {
        this.law = law;
    }

    public String getPrintHtml() {
        return printHtml;
    }

    public void setPrintHtml(String printHtml) {
        this.printHtml = printHtml;
    }

    public String getLogisticsPicturePath() {
        return logisticsPicturePath;
    }

    public void setLogisticsPicturePath(String logisticsPicturePath) {
        this.logisticsPicturePath = logisticsPicturePath;
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


    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", law=" + law +
                ", printHtml='" + printHtml + '\'' +
                ", logisticsPicturePath='" + logisticsPicturePath + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }
}
