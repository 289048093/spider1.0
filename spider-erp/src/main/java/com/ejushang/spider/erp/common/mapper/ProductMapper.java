package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.ProdSales;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.query.ProdNameQuery;
import com.ejushang.spider.query.ProdNoQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 */
public interface ProductMapper {
    /**
     * 数据层 插入一个产品
     *
     * @param product 一个产品的信息
     */
    public int saveProduct(Product product);

    /**
     * 数据层 插入一个产品出售消息
     *
     * @param prodSales 一个产品的信息
     */
    public int saveProdSales(ProdSales prodSales);

    /**
     * 数据层 通过ID获得一个产品
     *
     * @return
     * @paramc 产品
     */
    public Product findProductById(Integer id);

    /**
     * 数据层 通过CID获得一个产品
     *
     * @return
     * @paramc 产品
     */
    public Product findProductByCid(Integer cid);

    /**
     * 数据层 通过BrandId获得一个产品
     *
     * @return
     * @paramc 产品
     */
    public Product findProductByBrandId(Integer brandId);


    /**
     * 数据层 通过SKU获得一个产品
     *
     * @return
     * @paramc 产品
     */
    public Product findProductBySKU(Product product);

    /**
     * 数据层 获得所有的产品
     *
     * @return 返回一个 产品集合
     */
    public List<Product> findProductAll(@Param("product") ProductQuery productQuery, @Param("page") Page page);

    /**
     * 修改产品的信息
     *
     * @param product
     */
    public int updateProduct(Product product);

    /**
     * 获取最大的产品ID
     *
     * @return 产品ID
     */
    public Integer findProductMaxId();

    /**
     * 删除一个产品
     *
     * @param product
     */
    public int deleteProduct(Product product);

    /**
     * 删除一个产品通过ID
     *
     * @param id
     */
    public int deleteProductById(int id);

    /**
     * 查询中条数
     *
     * @return
     */
    public int findProductCount();

    /**
     * 根据商品编号查询产品
     *
     * @return
     */
    public List<Product> findProductByProdNo(ProdNoQuery prodNoQuery);

    /**
     * 根据商品名查询产品
     */
    public List<Product> findProductByProdName(ProdNameQuery prodNameQuery);

    /**
     * 根据商品销售实体中的商品id更改 商品销售数量
     *
     * @param prodSales
     * @return
     */
    public Integer updateProdSalesSaleCount(ProdSales prodSales);
}
