package com.ejushang.spider.erp.service.gift;

import com.ejushang.spider.domain.GiftBrand;
import com.ejushang.spider.domain.GiftBrandItem;
import com.ejushang.spider.query.GiftBrandQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.gift.IGiftBrandService;
import com.ejushang.spider.service.product.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午10:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-erp.xml")
public class GiftBrandServiceTest {
    @Autowired
    private IGiftBrandService giftBrandService;

    private IProductService productService;
    /**
     * 规则-品牌查询
     */
    /*
    @Test
    public void testFindAll() {
        List<GiftBrand> giftBrands = giftBrandService.findAll();
        for(GiftBrand giftBrand:giftBrands){
            System.out.println(giftBrand.toString());
        }
        Assert.assertEquals("测试成功", giftBrands, giftBrands);
        //assertThat(giftBrands, notNullValue());
    }

    @Test
    public void findByPage(){
        List<GiftBrandVO> giftBrandVOs = giftBrandService.findByPage();
        for(GiftBrandVO giftBrand:giftBrandVOs){
            System.out.println(giftBrand.toString());
        }
        assertThat(giftBrandVOs,notNullValue());
    }
     */

    @Test
    public void testFindByQuery(){
        GiftBrandQuery giftBrandQuery = new GiftBrandQuery();
        giftBrandQuery.setId(261);
        List<GiftBrand> giftBrands = giftBrandService.findByQuery(giftBrandQuery);
        System.out.println("1131"+giftBrands.toString());
        assertThat(giftBrands, notNullValue());
    }

    /**
     * 根据id 规则-品牌查询
     */
    @Test
    public void testFindGiftBrandById() {
        GiftBrand giftBrand = giftBrandService.findGiftBrandById(2);
        assertThat(giftBrand, notNullValue());
    }

    /**
     * 根据giftBrand查找这个id所有的奖励价格范围
     *
     * @return GiftBrand
     */
    @Test
    public void findBeginEnd() {
        List<GiftBrand> giftBrands = giftBrandService.findBeginEndByBrandId(1);
        for (GiftBrand giftBrand1 : giftBrands) {
            System.out.println(giftBrand1.getId() + " " + giftBrand1.getBrandId() + " " + giftBrand1.getPriceBegin() + " " + giftBrand1.getPriceEnd());
        }
        assertThat(giftBrands,notNullValue());
    }

    @Test
    public void findProdIdAndName(){
        ProductQuery productQuery = new ProductQuery();
        productQuery.setType("GIFT");
       // List<ProductVo> productVos = productService.findProductAll(productQuery);
        //assertThat(productVos,notNullValue());
    }

    /**
     * 规则-品牌新增
     */
    @Test
    public void testSaveGiftBrand() {
        GiftBrand giftBrand = new GiftBrand();
        giftBrand.setBrandId(215);
        giftBrand.setPriceBegin(901L);
        giftBrand.setPriceEnd(1000L);
        List<GiftBrandItem> giftBrandItemList = new ArrayList<GiftBrandItem>();
        GiftBrandItem giftBrandItem = new GiftBrandItem();
        giftBrandItem.setGiftBrandId(261);
        giftBrandItem.setGiftProdId(223879);
        giftBrandItem.setGiftProdCount(5);
        giftBrandItemList.add(giftBrandItem);
        giftBrand.setGiftBrandItemList(giftBrandItemList);
//        giftBrand.setGiftProdId(1);
//        giftBrand.setGiftProdCount(2);
        giftBrand.setInUse(true);
        int count = giftBrandService.saveGiftBrand(giftBrand);
        //Assert.assertEquals("success", count, count);
        if(count == 0 ){
            System.out.println("已经有这个范围了");
        }  else{
            System.out.println("插入成功");
        }
        assertThat(count, is(equalTo(1)));
    }

    /**
     * 规则-品牌删除
     */
    @Test
    public void testDeleteGiftBrandById() {
        Integer[] ids = {261};
        int count = giftBrandService.deleteGiftBrandByIds(ids);
        assertThat(count, is(equalTo(1)));
    }

    /**
     * 规则-品牌更新
     */
    @Test
    public void testUpdateGiftBrandById() {
        GiftBrand giftBrand = new GiftBrand();
        giftBrand.setId(261);
        giftBrand.setBrandId(316);
        giftBrand.setPriceBegin(101L);
        giftBrand.setPriceEnd(121L);
        giftBrand.setInUse(false);
        List<GiftBrandItem> giftBrandItemList = new ArrayList<GiftBrandItem>();
        GiftBrandItem giftBrandItem = new GiftBrandItem();
        giftBrandItem.setGiftBrandId(261);
        giftBrandItem.setGiftProdId(223879);
        giftBrandItem.setGiftProdCount(15);
        giftBrandItemList.add(giftBrandItem);
        giftBrand.setGiftBrandItemList(giftBrandItemList);
        int count = giftBrandService.updateGiftBrandByGiftBrand(giftBrand);
        assertThat(count, is(equalTo(1)));
    }
}
