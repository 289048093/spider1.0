package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 发票设计实体类
 */


public class InvoicePrintinfo {

    private Integer id;
    /**
     * 发票信息打印的lodop代码
     */
    private String printHtml;
    /**
     * 发票图片路径
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "InvoicePrintinfo{" +
                "id=" + id +
                ", printHtml='" + printHtml + '\'' +
                ", logisticsPicturePath='" + logisticsPicturePath + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                '}';
    }
}
