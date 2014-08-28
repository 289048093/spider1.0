package com.ejushang.spider.bean;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 14-3-5
 * Time: 下午4:48
 */
public class TransferInfoBean {

    /** 物流单号 */
    private String expressNo;

    /** 中转信息 */
    private String context;

    /** 中转日期 */
    private Date transferTime;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }
}
