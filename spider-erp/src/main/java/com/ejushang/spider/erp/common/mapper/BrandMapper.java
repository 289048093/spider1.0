package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 */
public interface BrandMapper {
    /**
     * 数据层 插入一个产品
     *
     * @param brand 一个产品的信息
     */
    public int saveBrand(Brand brand);

    /**
     * 数据层 通过ID获得一个产品
     *
     * @return
     * @paramc 产品ID
     */
    public Brand findBrandById(Integer id);

    /**
     * 数据层 通过ID获得一个产品
     *
     * @return
     * @paramc 产品ID
     */
    public Brand findBrandByName(Integer name);

    /**
     * 数据层 通过name获得一个产品
     *
     * @return
     * @paramc 产品名
     */
    public Brand findBrandByName(String name);

    /**
     * 数据层 获得所有的产品
     *
     * @return 返回一个 产品集合
     */
    public List<Brand> findBrandAll(@Param("brand") BrandQuery brandQuery, @Param("page") Page page);

    /**
     * 数据层 获得所有的产品数量
     *
     * @return 返回一个 产品集合
     */
    public Integer findBrandCount();

    /**
     * 修改产品的信息
     *
     * @param brand
     */
    public int updateBrand(Brand brand);


    /**
     * @param id
     */
    public int deleteBrand(Integer id);
}
