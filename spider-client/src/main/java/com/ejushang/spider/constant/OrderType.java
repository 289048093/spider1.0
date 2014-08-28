package com.ejushang.spider.constant;

/**
 * 订单类型
 * User: liubin
 * Date: 13-12-30
 */
public enum OrderType {

    NORMAL("正常订单"),

    REJECTED("退货订单"),

    EXCHANGED("换货订单"),

    COMPENSATED("补发订单"),

    INVOICED("需开票订单"),

    REFRESH("刷单订单");

    public String value;

    OrderType(String value) {
        this.value = value;
    }

    public String toDesc() {
        return value;
    }

    /**
     * 根据值取枚举
     * @param value
     * @return
     */
    public static OrderType enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(OrderType enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }
}
