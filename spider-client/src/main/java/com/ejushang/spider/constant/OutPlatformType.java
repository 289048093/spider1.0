package com.ejushang.spider.constant;

/**
 * User: Baron.Zhang
 * Date: 14-1-7
 * Time: 下午5:14
 */
public enum OutPlatformType {

    // 淘宝
    TAO_BAO("taobao"),
    // 京东
    JING_DONG("jd"),
    //蜘蛛网（针对手动下单的情况）
    SW_SPIDER("sw")
    ;

    public String value;

    OutPlatformType(String value){
        this.value = value;
    }

    public String toDesc(){
        return this.value;
    }

    /**
     * 根据值取枚举
     * @param value
     * @return
     */
    public static OutPlatformType enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(OutPlatformType enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }
}
