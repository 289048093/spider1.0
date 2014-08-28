package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.ProdSales;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 上午10:34
 */
public interface ProdSalesMapper {

    /**
     * 查询所有记录
     *
     * @return 返回商品销量记录
     */
    public List<ProdSales> findAll();

    /**
     * 根据商品编号查找该商品有销售记录
     * @param prodId
     * @return 该商品的销售记录
     */
    public ProdSales findProdSalesByProdId(Integer prodId);
    /**
     * 根据商品编号查找该商品有销售记录用于判断是否可以删除
     * @param
     * @return 该商品的销售记录
     */
    public Integer findProdSalesByIsDelete(Integer prodId);

    /**
     * 根据商品销售实体中的商品id更改 商品销售数量
     *
     * @param prodSales
     * @return 本次该商品销售数量
     */
    public Integer updateProdSalesSaleCount(ProdSales prodSales);

}
