package com.ejushang.spider.bean;

/**
 * User: Baron.Zhang
 * Date: 14-1-18
 * Time: 下午1:09
 */
public class ShopBean {

    /** 店铺id */
    private String shopId;

    /** 店铺标题（名称） */
    private String title;

    /** 卖家用户id */
    private String userId;

    /** 卖家用户昵称 */
    private String sellerNick;

    /** sessionKey */
    private String sessionKey;

    /** refreshToken */
    private String refreshToken;

    /** 平台类型 */
    private String outPlatformType;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOutPlatformType() {
        return outPlatformType;
    }

    public void setOutPlatformType(String outPlatformType) {
        this.outPlatformType = outPlatformType;
    }

    public String toString() {
        return "ShopBean={" +
                "shopId = " + shopId + "," +
                "userId = " + userId + "," +
                "sessionKey = " + sessionKey + "," +
                "refreshToken = " + refreshToken + "," +
                "outPlatformType = " + outPlatformType + "," +
                "}";
    }
}
