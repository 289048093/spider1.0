package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.ProdCategory;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.ProdCategoryQuery;
import com.ejushang.spider.service.product.IProdCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.InvocationTargetException;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 下午2:10
 * 物流service 测试类
 */
public class ProdCategoryServiceTest extends ErpTest{
    @Autowired
    private IProdCategoryService prodCategorydService;

    @Test
    @Rollback(false)
    public void insertProdCategoryTest() {
        ProdCategory prodCategory = new ProdCategory();
        prodCategory.setName("产品1");
        int count=prodCategorydService.saveProdCategory(prodCategory);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void updateProdCategoryTest() {
        ProdCategory prodCategory = new ProdCategory();
        prodCategory.setId(1);
        prodCategory.setName("大风起兴,龙飞翔");
        int count=prodCategorydService.updateProdCategory(prodCategory);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void deleteProdCategoryTest() {
        int [] idArray={1};
        int count=prodCategorydService.deleteProdCategory(idArray);
        assertEquals("测试失败",1,count);
    }

    @Test
    @Rollback(false)
    public void findProdCategoryAllTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       System.out.println( prodCategorydService.findProdCategoryForList(new ProdCategoryQuery()));
    }


}

