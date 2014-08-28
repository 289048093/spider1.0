package com.ejushang.spider.web.query;

/**
 * User: Json.zhu
 * Date: 13-12-26
 * Time: 上午10:30
 * 普通javaBean，封装数据发送到前台
 */
public class OrderByConditionsSelect {
    private String dateType;//日期类型
    private String startDate;//开始日期
    private String startHour;//开始的小时
    private String endDate;//结束日期
    private String endHour;//结束的小时
    private String shopName;//店铺
    private String storageName;//库房
    private String shippingComp;//快递
    private String tradeState;//交易状态
    private String orderState;//订单状态
    private String conditionQuery;//右边条件
    private String conditionType;//右边条件类型
    private String conditionValue; //右边值
    private String orderType;//订单类型


    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }



    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getShippingComp() {
        return shippingComp;
    }

    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getConditionQuery() {
        return conditionQuery;
    }

    public void setConditionQuery(String conditionQuery) {
        this.conditionQuery = conditionQuery;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

}
