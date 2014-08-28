package com.ejushang.spider.service.prodSales;

import com.ejushang.spider.domain.ProdSales;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 下午6:08
 */
public interface IProdSalesService {

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
     * 根据商品销售实体中的商品id更改 商品销售数量 **添加或减少**
     *
     * @param productId
     * @param quantity
     * @return 本次商品销售数量
     */
    public Integer updateProdSalesSaleCount(Integer productId, Integer quantity);

}
