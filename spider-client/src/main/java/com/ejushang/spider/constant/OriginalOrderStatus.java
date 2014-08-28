package com.ejushang.spider.constant;

/**
 * 原始订单状态枚举
 * User: Baron.Zhang
 * Date: 14-1-6
 * Time: 下午2:19
 */
public enum OriginalOrderStatus {

    // 没有创建支付宝交易
    TRADE_NO_CREATE_PAY("TRADE_NO_CREATE_PAY","没有创建支付宝交易"),
    // 等待买家付款
    WAIT_BUYER_PAY("WAIT_BUYER_PAY","等待买家付款"),
    // 等待卖家发货,即:买家已付款
    WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS","等待卖家发货,即:买家已付款"),
    // 等待买家确认收货,即:卖家已发货
    WAIT_BUYER_CONFIRM_GOODS("WAIT_BUYER_CONFIRM_GOODS","等待买家确认收货,即:卖家已发货"),
    // 买家已签收,货到付款专用
    TRADE_BUYER_SIGNED("TRADE_BUYER_SIGNED","买家已签收,货到付款专用"),
    // 交易成功
    TRADE_FINISHED("TRADE_FINISHED","交易成功"),
    // 付款以后用户退款成功，交易自动关闭
    TRADE_CLOSED("TRADE_CLOSED","付款以后用户退款成功，交易自动关闭"),
    // 付款以前，卖家或买家主动关闭交易
    TRADE_CLOSED_BY_TAOBAO("TRADE_CLOSED_BY_TAOBAO","付款以前，卖家或买家主动关闭交易")
    ;

    public String value;

    public String name;

    OriginalOrderStatus(String value){
        this.value = value;
    }

    OriginalOrderStatus(String value,String name){
        this.value = value;
        this.name = name;
    }

    public String toDesc(){
        return this.value;
    }

    /**
     * 根据值取枚举
     * @param value
     * @return
     */
    public static OriginalOrderStatus enumValueOf(String value) {
        if(value == null) {
            return null;
        }
        for(OriginalOrderStatus enumValue : values()) {
            if(value.equalsIgnoreCase(enumValue.value)) {
                return enumValue;
            }
        }
        return null;
    }

}
