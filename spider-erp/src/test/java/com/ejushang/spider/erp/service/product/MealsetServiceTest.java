package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.Mealset;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.MealsetQuery;
import com.ejushang.spider.service.product.IMealSetService;
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
public class MealsetServiceTest extends ErpTest{
    @Autowired
    private IMealSetService mealSetService;

    @Test
    @Rollback(false)
    public void insertMealsetTest() {
        Mealset mealset = new Mealset();
        mealset.setName("套餐1");
        mealset.setCode("153674618");
        int count=mealSetService.saveMealset(mealset);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void updateMealsetTest() {
        Mealset mealset = new Mealset();
        mealset.setId(4);
        mealset.setName("套餐2");
        mealset.setCode("153674618");
        mealset.setSellDescription("123123");
       int count=mealSetService.updateMealset(mealset);
        assertEquals("测试失败", 0, count);
    }

    @Test
    @Rollback(false)
    public void deleteMealsetTest() {
        int[]  idArray={1};
        int count=mealSetService.deleteMealset(idArray);
       assertEquals("修改删除标记失败",1,count);

    }

    @Test
    @Rollback(false)
    public void findMealsetAllTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       System.out.println( mealSetService.findMealsetAll(new MealsetQuery()));
    }

    @Test
    @Rollback(false)
    public void findMealsetBySKUTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println(mealSetService.findMealsetBySKU("TU20140116123369"));
    }


}

