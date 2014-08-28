package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Mealset;
import com.ejushang.spider.query.MealsetQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 */
public interface MealsetMapper {
    /**
     * 数据层 插入一个套餐
     *
     * @param brand 一个套餐的信息
     */
    public int saveMealset(Mealset brand);

    /**
     * 数据层 通过ID获得一个套餐
     *
     * @return
     * @paramc 套餐ID
     */
    public Mealset findMealsetById(Integer id);

    /**
     * 数据层 通过ID获得一个套餐
     *
     * @return
     * @paramc 套餐ID
     */
    public List<Mealset> findMealsetByName(String name);

    /**
     * 数据层 获得所有的套餐
     *
     * @return 返回一个 套餐集合
     */
    public List<Mealset> findMealsetAll(@Param("mealset") MealsetQuery mealsetQuery, @Param("page") Page page);

    /**
     * 修改套餐的信息
     *
     * @param brand
     */
    public int updateMealset(Mealset brand);

    /**
     * 获取最大的套餐ID
     *
     * @return 套餐ID
     */
    public Integer findMealsetMaxId();

    /**
     * 删除一个套餐
     *
     * @param id
     */
    public int deleteMealset(Integer id);

    /**
     * 通过SKU获得一个套餐
     *
     * @return套餐信息
     */
    public Mealset findMealsetBySKU(String code);
}
