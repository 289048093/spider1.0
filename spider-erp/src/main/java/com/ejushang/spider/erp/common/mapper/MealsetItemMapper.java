package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.query.MealsetItemQuery;

import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 */
public interface MealsetItemMapper {
    /**
     * 数据层 插入一个套餐组
     *
     * @param brand 一个套餐的信息
     */
    public int saveMealsetItem(MealsetItem brand);

    /**
     * 数据层 通过ID获得一个套餐
     *
     * @return
     * @paramc 套餐ID
     */
    public MealsetItem findMealsetItemById(Integer id);

    /**
     * 数据层 通过套餐ID获得所有的套餐项
     *
     * @return
     * @paramc 套餐ID
     */
    public MealsetItem findMealsetItemByMealId(Integer id);

    /**
     * 数据层 获得所有的套餐
     *
     * @return 返回一个 套餐集合
     */
    public List<MealsetItem> findMealsetItemAll(MealsetItemQuery mealsetItemQuery);

    /**
     * 修改套餐的信息
     *
     * @param brand
     */
    public int updateMealsetItem(MealsetItem brand);

    /**
     * 获取最大的套餐ID
     *
     * @return 套餐ID
     */
    public Integer findMealsetItemMaxId();

    /**
     * 删除一个套餐
     *
     * @param id
     */
    public int deleteMealsetItem(Integer id);

    /**
     * 删除一个套餐
     *
     * @param prodId
     */
    public int deleteMealsetItemByProdId(Integer prodId);

    /**
     * 删除一个套餐项 通过套餐id
     *
     * @param mealId
     */
    public int deleteMealsetItemByMealId(Integer mealId);

}
