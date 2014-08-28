package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.query.ShopQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 14-1-7
 * Time: 上午9:31
 */
public interface ShopMapper {

    /**
     * 店铺查询
     * @param shopQuery
     * @return
     */
    public List<Shop> findShopByQuery(ShopQuery shopQuery);

    /**
     * 店铺分页查询
     * @param shopQuery
     * @param page
     * @return
     */
    public List<Shop> findShopPageByQuery(@Param("shopQuery")ShopQuery shopQuery,@Param("page")Page page);

    /**
     * 获取店铺记录总条数
     * @param shopQuery
     * @return
     */
    public Integer getShopCountByQuery(ShopQuery shopQuery);

    /**
     * 店铺新增
     * @param shop
     * @return
     */
    public int saveShop(Shop shop);

    /**
     * 店铺更新
     * @param shop
     * @return
     */
    public int updateShop(Shop shop);

    /**
     * 店铺删除
     * @param shopId
     * @return
     */
    public int deleteShop(String shopId);

    /**
     * 根据店铺title查询店铺
     */
    public Shop findShopByShopId(Integer shopId);

    public List<Shop> findAllShop();

}
