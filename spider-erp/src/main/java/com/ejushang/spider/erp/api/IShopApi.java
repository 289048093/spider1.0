package com.ejushang.spider.erp.api;

import com.ejushang.spider.domain.Shop;

/**
 * User: Baron.Zhang
 * Date: 14-1-9
 * Time: 下午5:38
 */
public interface IShopApi {

    /**
     * 同步店铺信息至外部平台
     * @param shop
     * @return
     */
    public Boolean updateShop(Shop shop,String sessionKey) throws Exception;
}
