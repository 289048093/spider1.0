package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.ShopAuth;
import com.ejushang.spider.query.ShopAuthQuery;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 14-1-7
 * Time: 上午9:31
 */
public interface ShopAuthMapper {

    /**
     * 店铺授权查询
     * @param shopAuthQuery
     * @return
     */
    public List<ShopAuth> findShopAuthByQuery(ShopAuthQuery shopAuthQuery);

    /**
     * 根据店铺一览查询
     * @param shopList
     * @return
     */
    public List<ShopAuth> findShopAuthByShopList(List<Shop> shopList);

    /**
     * 店铺授权新增
     * @param shopAuth
     * @return
     */
    public int saveShopAuth(ShopAuth shopAuth);

    /**
     * 店铺授权更新
     * @param shopAuth
     * @return
     */
    public int updateShopAuth(ShopAuth shopAuth);

    /**
     * 店铺授权删除
     * @param shopId
     * @return
     */
    public int deleteShopAuth(String shopId);
}
