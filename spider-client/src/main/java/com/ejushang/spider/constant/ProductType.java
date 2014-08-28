package com.ejushang.spider.constant;

/**
 * 产品类型
 * User: liubin
 * Date: 13-12-30
 */
public enum ProductType {

    PRODUCT("商品"),

    GIFT("赠品");

    public String value;

    ProductType(String value) {
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
    public static ProductType enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(ProductType enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }
}
