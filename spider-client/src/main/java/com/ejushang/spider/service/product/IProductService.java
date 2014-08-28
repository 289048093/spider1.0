package com.ejushang.spider.service.product;

import com.ejushang.spider.constant.OrderItemType;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.QueryProdVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:56
 */
public interface IProductService {
    /**
     * 数据层 保存产品信息
     *
     * @param product 一个产品的信息
     * @return 插入的条数
     */
    public int saveProduct(Product product, Storage storage);

    /**
     * 数据层 通过ID获得一个产品
     *
     * @return 获得产品对象
     * @paramc 产品ID
     */
    public Product findProductById(Integer id);


    /**
     * 数据层 通过Sku获得一个产品
     *
     * @return 获得产品对象
     * @paramc sku
     */
    public Product findProductBySKU(String prodCode, String type);


    /**
     * 数据层 获得所有的产品
     *
     * @return 返回一个 产品集合
     */
    public Page findProductAll(ProductQuery product) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 修改产品的信息
     *
     * @param product 需要修改信息
     * @return 修改的条数
     */
    public int updateProduct(Product product, Storage storage);

    /**
     * 获取最大的产品ID
     *
     * @return 产品ID
     */
    public Integer findProductMaxId();

    /**
     * 删除一个产品
     *
     * @param idArray
     * @return 删除的条数
     */
    public int deleteProduct(int[] idArray);

    /**
     * 查询中条数
     *
     * @return
     */
    public int findProductCount(ProductQuery productQuery);

    /**
     * 验证过后 保存商品 加入库存
     *
     * @return
     */
    public void leadInProduct(HSSFSheet hssfSheet);

    /**
     * 根据商品属性查询产品
     *
     * @param
     * @param orderItemType
     * @return
     */
    public List<QueryProdVo> findProductByProd(String param, String paramType, OrderItemType orderItemType,Integer repoId);




    /**
     * 得到邮费虚拟商品,该商品的SKU在配置在系统配置表里
     * @return
     */
    public Product getPostageProduct();
}
