package com.ejushang.spider.vo;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午11:55
 */
public class OrderFetchVo {
    /** 主键编号 */
    private Integer id;
    /** 订单抓取时间 */
    private Date fetchTime;
    /** 抓取平台 */
    private String outPlatform;
    /** 抓取的店铺ID */
    private Long shopId;
    /** 抓取条数 */
    private Integer fetchCount;
    /** 抓取的店铺名*/
    private String title;
    /** 记录创建时间 */
    private Date createTime;
    /** 记录更新时间 */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

    public String getOutPlatform() {
        return outPlatform;
    }

    public void setOutPlatform(String outPlatform) {
        this.outPlatform = outPlatform;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(Integer fetchCount) {
        this.fetchCount = fetchCount;
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


    @Override
    public String toString() {
        return "OrderFetchVo{" +
                "id=" + id +
                ", fetchTime=" + fetchTime +
                ", outPlatform='" + outPlatform + '\'' +
                ", shopId=" + shopId +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
