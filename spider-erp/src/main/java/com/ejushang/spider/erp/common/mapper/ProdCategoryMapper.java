package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.ProdCategory;
import com.ejushang.spider.query.ProdCategoryQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 */
public interface ProdCategoryMapper {
    /**
     * 数据层 插入一个产品分类
     *
     * @param brand 一个产品分类的信息
     */
    public int saveProdCategory(ProdCategory brand);

    /**
     * 数据层 通过ID获得一个产品分类
     *
     * @return
     * @paramc 产品分类ID
     */
    public ProdCategory findProdCategoryById(Integer id);

    /**
     * 数据层 通过name获得一个产品分类
     *
     * @return
     * @paramc 产品分类名
     */
    public ProdCategory findProdCategoryByName(String name);

    /**
     * 数据层 获得所有的产品分类
     *
     * @return 返回一个 产品分类集合
     */
    public List<ProdCategory> findProdCategoryAll(@Param("prodCategory") ProdCategoryQuery prodCategoryQuery, @Param("page") Page page);

    /**
     * 数据层 产品分类数量
     *
     * @return 返回一个 产品分类集合
     */
    public int findProdCategoryCount();

    /**
     * 修改产品分类的信息
     *
     * @param brand
     */
    public int updateProdCategory(ProdCategory brand);

    /**
     * 获取最大的产品分类ID
     *
     * @return 产品分类ID
     */
    public Integer findProdCategoryMaxId();

    /**
     * 删除一个产品分类
     *
     * @param id
     */
    public int deleteProdCategory(Integer id);
}
