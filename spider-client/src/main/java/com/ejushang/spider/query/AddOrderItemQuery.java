package com.ejushang.spider.query;

/**
 * User:moon
 * Date: 14-1-20
 * Time: 下午4:41
 */
public class AddOrderItemQuery {

    private  String skuCode;

    private  Integer orderId;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "AddOrderItemQuery{" +
                "skuCode='" + skuCode + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
