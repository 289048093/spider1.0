package com.ejushang.spider.constant;

/**
 * 库存数量类型
 * User: liubin
 * Date: 13-12-30
 */
public enum StorageQuantityType {

    PHOTO_QUANTITY("图片库存"),

    THEORY_QUANTITY("理论库存"),

    PHYSICAL_QUANTITY("实际库存");

    public String value;

    StorageQuantityType(String value) {
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
    public static StorageQuantityType enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(StorageQuantityType enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }
}
