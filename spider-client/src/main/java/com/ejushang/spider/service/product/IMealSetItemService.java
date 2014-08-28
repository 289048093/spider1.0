package com.ejushang.spider.service.product;

import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.query.MealsetItemQuery;
import com.ejushang.spider.vo.MealsetItemVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 下午1:19
 */
public interface IMealSetItemService {

    /**
     * 数据层 插入一个套餐项
     *
     * @param mealsetItemArray 一个套餐项的信息
     * @return 插入的条数
     */
    public int saveMealsetItem(MealsetItem [] mealsetItemArray);

//    /**
//     * 数据层 通过ID获得一个套餐项
//     *
//     * @return
//     * @paramc 套餐项ID
//     */
//    public MealsetItem findMealsetItemById(Integer id);

    /**
     * 数据层 获得所有的套餐项
     *
     * @return 返回一个 套餐项集合
     */
    public List<MealsetItemVo> findMealsetItemAll(MealsetItemQuery mealsetItemQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 修改套餐项的信息
     *
     * @param mealsetItemsArray
     * @return 修改的条数
     */
    public int updateMealsetItem(MealsetItem[] mealsetItemsArray);

//    /**
//     * 获取最大的套餐项ID
//     *
//     * @return 套餐项ID
//     */
//    public Integer findMealsetItemMaxId();

    /**
     * 删除一个套餐项
     *
     * @param id
     * @return 删除的条数
     */
    public int deleteMealsetItem(int [] id);
}
