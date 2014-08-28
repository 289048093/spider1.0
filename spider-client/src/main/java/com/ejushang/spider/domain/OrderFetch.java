package com.ejushang.spider.domain;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午11:55
 */
public class OrderFetch {
    /** 主键编号 */
    private Integer id;
    /** 订单抓取时间 */
    private Date fetchTime;
    /** 抓取平台 */
    private String outPlatform;
    /** 抓取的店铺ID */
    private String shopId;
    /** 抓取到的记录数 */
    private Integer fetchCount;
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

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

    public String getOutPlatform() {
        return outPlatform;
    }

    public void setOutPlatform(String outPlatform) {
        this.outPlatform = outPlatform;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
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

    public String toString() {
        return "OrderFetch={" +
                "id = " + id + "," +
                "fetchTime = " + fetchTime + "," +
                "outPlatform = " + outPlatform + "," +
                "shopId = " + shopId + "," +
                "createTime = " + createTime + "," +
                "updateTime = " + updateTime + "," +
                "}";
    }
}
