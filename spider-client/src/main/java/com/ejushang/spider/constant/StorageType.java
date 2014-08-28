package com.ejushang.spider.constant;

/**
 * User: tin
 * Date: 14-4-11
 * Time: 上午11:53
 */
public enum StorageType {

    DELIVERY("确定发货"),
    STORAGE("库存界面修改");

    public String value;

    StorageType(String value) {
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
    public static StorageType enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(StorageType enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }

}
