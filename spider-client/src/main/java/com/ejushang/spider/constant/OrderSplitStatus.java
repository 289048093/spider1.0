package com.ejushang.spider.constant;

/**
 * 订单拆分状态
 * User: liubin
 * Date: 13-12-30
 */
public enum OrderSplitStatus {

    NORMAL("未拆分"),

    WAIT_SPLIT("等待拆分"),

    SPLIT_AUTO("自动拆分"),

    SPLIT_BY_HAND("手动拆分");

    public String value;

    OrderSplitStatus(String value) {
        this.value = value;
    }

    public String toDesc() {
        return value;
    }
}
