package com.ejushang.spider.constant;

/**
 * 订单明细类型
 * User: liubin
 * Date: 13-12-30
 */
public enum OrderItemType {

    PRODUCT("商品"),

    MEALSET("套餐商品"),

    GIFT("赠品");

    public String value;

    OrderItemType(String value) {
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
    public static OrderItemType enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(OrderItemType enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }
}
