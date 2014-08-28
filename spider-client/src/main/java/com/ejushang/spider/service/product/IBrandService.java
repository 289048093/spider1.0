package com.ejushang.spider.service.product;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.util.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:55
 */
public interface IBrandService {

    /**
     * 数据层 插入一个产品
     *
     * @param brand 一个产品的信息
     * @return 插入的条数
     */
    public int saveBrand(Brand brand);



    /**
     * 数据层 获得所有的产品
     *
     * @return 返回一个 产品集合
     */
    public Page findBrandAll(BrandQuery brandQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 通过ID获得品牌信息
     * @param id
     * @return
     */
    public Brand findBrandById(Integer id);

    /**
     * 通过name获得品牌信息
     * @param name
     * @return
     */
    public Brand findBrandByName(String name);
    /**
     * 修改产品的信息
     *
     * @param brand
     * @return 修改的条数
     */
    public int updateBrand(Brand brand);
    /**
     * 删除一个产品
     *
     * @param idArray
     * @return 删除的条数
     */
    public int deleteBrand(int [] idArray);

    /**
     * 查询所有品牌，用于在下拉框中显示
     * @return
     */
    public List<Brand> findBrandForList(BrandQuery brandQuery);
}
