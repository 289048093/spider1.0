package com.ejushang.spider.query;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 上午11:55
 */
public class OrderFetchQuery extends  BrandQuery{
    /** 主键编号 */
    private Integer id;
    /** 订单抓取时间 */
    private Date fetchTime;
    /** 抓取平台 */
    private String outPlatform;
    /** 抓取的店铺名 */
    private Long title;
    /** 抓取的店铺ID */
    private String shopId;
    /** 记录创建时间 */
    private Date createTime;
    /** 记录更新时间 */
    private Date updateTime;

    private String startTime; //开始日期
    private String endTime; //结束日期

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

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

    public Long getTitle() {
        return title;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setTitle(Long title) {
        this.title = title;
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
