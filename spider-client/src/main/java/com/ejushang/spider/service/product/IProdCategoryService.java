package com.ejushang.spider.service.product;


import com.ejushang.spider.domain.ProdCategory;
import com.ejushang.spider.query.ProdCategoryQuery;
import com.ejushang.spider.util.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:57
 */
public interface IProdCategoryService {
    /**
     * 数据层 插入一个产品分类
     *
     * @param prodCategory 产品分类信息
     * @return 插入的条数
     */
    public int saveProdCategory(ProdCategory prodCategory);

    /**
     * 数据层 通过ID获得一个产品
     *
     * @return  实体对象
     * @paramc 产品ID
     */
    public ProdCategory findProdCategoryById(Integer id);
    /**
     * 数据层 通过name获得一个产品
     *
     * @return
     * @paramc 产品名
     */
    public ProdCategory findProdCategoryByName(String name);

    /**
     * 数据层 获得所有的产品
     *
     * @return 返回一个 产品集合
     */
    public Page findProdCategoryAll(ProdCategoryQuery prodCategoryQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
    /**
     * 数据层 获得所有的产品用于下拉框显示
     *
     * @return 返回一个 产品集合
     */
    public List<ProdCategory> findProdCategoryForList(ProdCategoryQuery prodCategoryQuery);
    /**
     * 修改产品的信息
     *
     * @param prodCategory
     * @return 修改的条数
     */
    public int updateProdCategory(ProdCategory prodCategory);

    /**
     * 获取最大的产品ID
     *
     * @return 产品ID
     */
    public Integer findProdCategoryMaxId();

    /**
     * 删除一个产品
     *
     * @param id
     * @return 删除的条数
     */
    public int deleteProdCategory(int [] id);


}
