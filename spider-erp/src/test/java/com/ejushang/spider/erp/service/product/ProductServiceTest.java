package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.constant.OrderItemType;
import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.util.AppConfigUtil;
import com.ejushang.spider.vo.ProductVo;
import com.ejushang.spider.vo.QueryProdVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 下午2:10
 * 物流service 测试类
 */
public class ProductServiceTest extends ErpTest {
    @Autowired
    private IProductService productService;
    @Test
    @Rollback(false)
    public void insertProductTest() {
        Product product = new Product();
        product.setBrandId(2);
        product.setProdName("程序猿一只");
        product.setProdNo("123");
        product.setProdCode("123123");
        product.setCid(123123);
        product.setDescription("不知道");
        product.setPicUrl("天使与魔鬼");
        product.setType(ProductType.GIFT.toDesc());
        product.setColor("红");
        product.setShopPriceStr("234");
        product.setStandardPriceStr("232");
        product.setBuyPriceStr("12.12");
        product.setSpeci("x");
        product.setBoxSize("1*119*1");
        product.setWeight("190");
        /***添加库存信息*/
        Storage storage=new Storage();
        storage.setActuallyNumber(123);
        storage.setRepoId(56);
        int count = productService.saveProduct(product,storage );
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void updateProductTest() {
        Product product = new Product();
        product.setId(3);
        product.setDescription("123213");
        product.setProdName("程序猿一只");
        product.setProdNo("123");
        product.setProdCode("123123");
        product.setCid(123123);
        product.setDescription("不知道");
        product.setPicUrl("天使与魔鬼");
        product.setShopPriceStr("234");
        product.setStandardPriceStr("232");
        product.setBuyPriceStr("12.12");
        product.setColor("红");
        product.setSpeci("x");
        product.setBoxSize("1*119*1");
        product.setWeight("190");
        int count = productService.updateProduct(product, null);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void deleteProductTest() {
        int[] idArray = {12};
        int count = productService.deleteProduct(idArray);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void findProductAllTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ProductQuery productQuery = new ProductQuery();
        System.out.println("所有数据" + productService.findProductAll(productQuery).getResult());
//        List<ProductVo> productList=productService.findProductAll(productQuery).getResult();
//        for(int i=0;i<productList.size();i++){
//            ProductVo productVo=productList.get(i);
//            Product product=productService.findProductById(productVo.getId());
//            Storage storage=new Storage();
//            storage.setActuallyNumber(123);
//            storage.setRepoId(56);
//            int [] ids=new int[1];
//            ids[0]=productVo.getId();
//            productService.deleteProduct(ids);
//            productService.saveProduct(product,storage);
//        }
    }

    @Test
    @Rollback(false)
    public void findProductBySKUTest() {
        System.out.println(productService.findProductBySKU("2198489", ProductType.PRODUCT.toString()));
    }

    @Test
    @Rollback(false)
    public void findProductByIdTest() {
        System.out.println(productService.findProductById(11));
    }

    @Test
    @Rollback(false)
    public void findProductCount() {
        System.out.println(productService.findProductCount(new ProductQuery()));
    }

    @Test
    @Rollback(false)
    public void initProductTest() {
        List<Product> products = initProducts(ProductType.PRODUCT.toString(), 1);
        Product product = products.get(0);
        System.out.println(product.getProdCode());
    }

    @Test
    @Rollback(false)
    public void testfind() {
        //(String param,String paramType,OrderItemType orderItemType)
      //  List<QueryProdVo> queryProdVoList = productService.findProductByProd("套餐1", "PRODNAME", OrderItemType.MEALSET);
//        for (QueryProdVo queryProdVo : queryProdVoList) {
//            System.out.println(queryProdVo.getProdNo() + "/" + queryProdVo.getProdName() + "/" + queryProdVo.getSkuCode());
//        }

//        List<QueryProdVo> queryProdVoList1= productService.findProductByProd("商品1", "PRODNAME", OrderItemType.PRODUCT);
//        for(QueryProdVo queryProdVo:queryProdVoList1){
//            System.out.println(queryProdVo.getProdNo()+"/"+queryProdVo.getProdName()+"/"+queryProdVo.getProdCode());
//        }

      //  List<QueryProdVo> queryProdVoList1 = productService.findProductByProd("123", "PRODNO", OrderItemType.PRODUCT);
//        for (QueryProdVo queryProdVo : queryProdVoList1) {
//            System.out.println(queryProdVo.getProdNo() + "/" + queryProdVo.getProdName() + "/" + queryProdVo.getSkuCode());
//        }

      //  List<QueryProdVo> queryProdVoList11 = productService.findProductByProd("111", "PRODNAME", OrderItemType.PRODUCT);
//        for (QueryProdVo queryProdVo : queryProdVoList11) {
//            System.out.println(queryProdVo.getProdNo() + "/" + queryProdVo.getProdName() + "/" + queryProdVo.getSkuCode());
//        }
    }
}

