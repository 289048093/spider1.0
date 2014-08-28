package com.ejushang.spider.erp.service.product;


import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.MealsetItemQuery;
import com.ejushang.spider.service.product.IMealSetItemService;
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
public class MealsetItemServiceTest extends ErpTest {
    @Autowired
    private IMealSetItemService mealsetItemService;

    @Test
    @Rollback(false)
    public void insertMealsetItemTest() {
        MealsetItem mealsetItem = new MealsetItem();
        mealsetItem.setMealId(12313);
        mealsetItem.setProdId(123);
        mealsetItem.setMealCount(123);
        mealsetItem.setMealPrice(1233L);
        mealsetItem.setMealId(123);
        MealsetItem [] mealsetItems=new MealsetItem[5];
        mealsetItems[0]=mealsetItem;
        int count=mealsetItemService.saveMealsetItem(mealsetItems);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void updateMealsetItemTest() {
        MealsetItem mealsetItem = new MealsetItem();
        mealsetItem.setId(1);
        mealsetItem.setMealId(12313);
        mealsetItem.setProdId(123);
        mealsetItem.setMealCount(123);
        mealsetItem.setMealPrice(1233L);
        int count=mealsetItemService.updateMealsetItem(null);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void deleteMealsetItemTest() {
        int [] idArray={1};
        int count=mealsetItemService.deleteMealsetItem(idArray);
        assertEquals("测试失败",1,count);
    }

    @Test
    @Rollback(false)
    public void findMealsetItemAllTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       System.out.println( mealsetItemService.findMealsetItemAll(new MealsetItemQuery()));
    }


}

