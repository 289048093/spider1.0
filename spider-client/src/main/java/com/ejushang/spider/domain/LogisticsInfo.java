package com.ejushang.spider.domain;

import com.ejushang.spider.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 物流配送信息表
 *
 * create by Athens on 14-1-8
 */
public class LogisticsInfo {

    private Integer id;

    /** 订单号(一个订单在业务上只允许有一个快递单号) */
    private String orderNo;

    /** 物流单号(每一笔物流单号的数据都会向 kuaidi100 发送请求. 请求一次就需要付费! 所以, 请务必保证只在线上可以请求及只发送一次) */
    private String expressNo;

    /** 物流公司名(shunfeng, yunda, ems, tiantian等) */
    private String expressCompany;

    /** 收货地址(到市一级即可. 如广东省深圳市, 北京市) */
    private String sendTo;

    /** 物流信息(由 快递100 提供, 存储 json 字符串) */
    private String expressInfo;

    /** 物流状态(1 表示配送完成, 0 表示未完成), 默认是 0 */
    private Boolean expressStatus;

    /** 第一条状态的时间 */
    private Date firstTime;

    /** 物流状态最新的时间 */
    private Date latestTime;

    /** 是否已请求第三方物流(1已请求, 0未请求), 默认是 0 */
    private Boolean wasRequest;

    private Date createTime;

    private Date updateTime;

    public LogisticsInfo() {}

    /**
     * 用来添加
     *
     * @param orderNo 订单号
     * @param expressNo 物流单号
     * @param expressCompany 物流公司
     * @param sendTo 收货地址(到省一级即可. 如广东省, 北京市)
     */
    public LogisticsInfo(String orderNo, String expressNo, String expressCompany, String sendTo) {
        this.orderNo = orderNo;
        this.expressNo = expressNo;
        this.expressCompany = expressCompany;
        this.sendTo = sendTo;
    }

    /**
     * 记录向第三方平台发送了成功请求
     *
     * @param expressNo 物流单号
     * @param wasRequest 是否已请求第三方物流(1 已请求, 0未请求), 默认是 0 >> false
     */
    public LogisticsInfo(String expressNo, Boolean wasRequest) {
        this.expressNo = expressNo;
        this.wasRequest = wasRequest;
    }

    /**
     * 用来更新物流信息
     *
     * @param expressNo 物流单号
     * @param firstTime 第一条物流信息时间
     * @param latestTime 最新的物流信息时间
     * @param expressStatus 物流状态, 1 表示成功, 0 表示未成功
     * @param expressInfo 物流信息
     */
    public LogisticsInfo(String expressNo, Date firstTime, Date latestTime, Integer expressStatus, String expressInfo) {
        this.expressNo = expressNo;
        this.firstTime = firstTime;
        this.latestTime = latestTime;
        this.expressStatus = (expressStatus == 1);
        this.expressInfo = expressInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(String expressInfo) {
        this.expressInfo = expressInfo;
    }

    public Boolean getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Boolean expressStatus) {
        this.expressStatus = expressStatus;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public Boolean getWasRequest() {
        return wasRequest;
    }

    public void setWasRequest(Boolean wasRequest) {
        this.wasRequest = wasRequest;
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

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    @Override
    public String toString() {
        StringBuilder sbd = new StringBuilder();
        sbd.append("id:").append(id);
        sbd.append(", 订单号:").append(orderNo);
        sbd.append(", 快递单号:").append(expressNo);
        sbd.append(", 寄送到:").append(sendTo);
        if (StringUtils.isNotBlank(expressCompany))
            sbd.append(", 物流公司:").append(expressCompany);
        else
            sbd.append(", 未记录物流公司");

        // ignore info
        // sbd.append(expressInfo);
        if (firstTime != null)
            sbd.append(", 第一条物流信息时间:").append(DateUtils.formatDate(firstTime, DateUtils.DateFormatType.DATE_FORMAT_STR));
        if (latestTime != null)
            sbd.append(", 最新的物流信息时间:").append(DateUtils.formatDate(latestTime, DateUtils.DateFormatType.DATE_FORMAT_STR));

        if (createTime != null)
            sbd.append(", 创建时间:").append(DateUtils.formatDate(createTime, DateUtils.DateFormatType.DATE_FORMAT_STR));
        if (updateTime != null)
            sbd.append(", 更新时间:").append(DateUtils.formatDate(updateTime, DateUtils.DateFormatType.DATE_FORMAT_STR));

        sbd.append(", 配送状态:").append(expressStatus ? "已完成" : "未完成");

        return sbd.toString();
    }
}
