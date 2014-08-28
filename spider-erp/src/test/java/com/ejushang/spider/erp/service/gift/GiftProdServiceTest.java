package com.ejushang.spider.erp.service.gift;

import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.GiftProd;
import com.ejushang.spider.query.GiftProdQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.gift.IGiftProdService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.util.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午11:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-erp.xml")
public class GiftProdServiceTest {
    @Autowired
    public IGiftProdService giftProdService;

    @Autowired
    public IProductService productService;

    /**
     * 根据对象 查找所有记录
     */
    @Test
    public void testFindGiftProdAll(){
        GiftProdQuery giftProdQuery = new GiftProdQuery();
        giftProdQuery.setInUse(true);
        List<GiftProd> giftProds = giftProdService.findByQuery(giftProdQuery);
        System.out.println(giftProds);
        assertThat(giftProds,notNullValue());
    }

    @Test
    public void testFindProdAll(){
        ProductQuery productQuery = new ProductQuery();
//        productQuery.setType("PRODUCT");
//        productQuery.setLimit(1000);
//        productQuery.setPage(1);
        productQuery.setType(ProductType.GIFT.toString());
        Page page=new Page();
        page.setPageNo(200);
        //productQuery.setStart(0);
        productQuery.setPage(1);
       // List<ProductVo> productList = productService.findProductAll(productQuery);
       // System.out.println("productList:"+productList);

    }

    /**
     * 增加一条GiftProd记录
     */
    @Test
    public void testSaveGiftProd(){
        List<GiftProd> giftProd = new ArrayList<GiftProd>(1);
        giftProd.get(0).setSellProdId(1);
        giftProd.get(0).setGiftProdId(1131);
        giftProd.get(0).setGiftProdCount(3);
        giftProd.get(0).setInUse(false);
        int count = giftProdService.saveGiftProdList(giftProd);
        assertThat(count,is(equalTo(1)));
    }

    /**
     * 根据id删除一条记录
     */
    @Test
    public void testDeleteGiftProdById(){
        //int count = giftProdService.deleteGiftProdByIds(5);
       // assertThat(count,is(equalTo(1)));
    }

    /**
     * 根据id 修改该记录的 其他字段值
     */
    @Test
    public void testUpdateGiftProdById(){
        GiftProd giftProd = new GiftProd();
        giftProd.setId(5);
        giftProd.setSellProdId(123);
        giftProd.setInUse(true);
        int count = giftProdService.updateGiftProdById(giftProd);

        assertThat(count,is(equalTo(1)));
    }


}
