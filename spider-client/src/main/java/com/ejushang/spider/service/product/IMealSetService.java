package com.ejushang.spider.service.product;

import com.ejushang.spider.domain.Mealset;
import com.ejushang.spider.query.MealsetQuery;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.MealsetVo;

import java.lang.reflect.InvocationTargetException;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:57
 */
public interface IMealSetService {
    /**
     * 数据层 插入一个套餐
     *
     * @param brand 一个套餐的信息
     * @return 插入的条数
     */
    public int saveMealset(Mealset brand);

    /**
     * 数据层 通过ID获得一个套餐
     *
     * @return
     * @paramc 套餐ID
     */
    public MealsetVo findMealsetById(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 数据层 获得所有的套餐
     *
     * @return 返回一个 套餐集合
     */
    public Page findMealsetAll(MealsetQuery mealsetQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 修改套餐的信息
     *
     * @param brand
     * @return 修改的条数
     */
    public int updateMealset(Mealset brand);


    /**
     * 删除一个套餐
     *
     * @param idArray
     * @return 删除的条数
     */
    public int deleteMealset(int [] idArray);

    /**
     * 通过SKU获得一个套餐
     * @return   套餐信息
     */
    public Mealset findMealsetBySKU(String sku) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
