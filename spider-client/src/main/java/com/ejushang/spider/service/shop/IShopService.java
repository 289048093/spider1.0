package com.ejushang.spider.service.shop;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.ShopAuth;
import com.ejushang.spider.query.ShopQuery;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.ShopVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 14-1-6
 * Time: 下午5:28
 */
public interface IShopService {

    /**
     * 店铺查询
     *
     * @param shopQuery
     * @return
     */
    public Page findShopPageByQuery(ShopQuery shopQuery) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    /**
     * 店铺查询，根据店铺id查询店铺信息
     * @param shopId
     * @return
     */
    public Shop findShopByShopId(String shopId);

    /**
     * 店铺明细查询
     * @param id
     * @return
     */
    public ShopVo findShopById(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 店铺新增
     * @param shop
     */
    public void saveShop(Shop shop);

    /**
     * 同时新增店铺和店铺授权信息
     * @param shop
     * @param shopAuth
     */
    public void saveShopAndShopAuth(Shop shop,ShopAuth shopAuth);

    /**
     * 店铺更新
     * @param shop
     */
    public void updateShop(Shop shop,String sessionKey) throws Exception;

    /**
     * 店铺授权信息更新
     * @param shopAuth
     */
    public void updateShopAuth(ShopAuth shopAuth);

    /**
     * 店铺删除
     * @param shopId
     */
    public void deleteShop(String shopId);

    /**
     * 动态获取评分
     * @param id
     * @return
     */
    public ShopVo dynamicGetScore(Integer id) throws Exception;

    public Shop findShopByShopId(Integer shopId);

    /**
     * 获取所有店铺及其授权信息
     * @return
     */
    public List<ShopBean> findAllShopBean();

    public List<Shop> findAllShop();

    /**
     * 根据昵称查找店铺
     * @return
     */
    public Shop findShopByNick(String nick);

}
