package com.ejushang.spider.erp.service.delivery;

import com.ejushang.spider.domain.Delivery;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.DeliveryQuery;
import com.ejushang.spider.util.DateUtils;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;


/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 下午2:10
 * 物流service 测试类
 */
public class DeliveryServiceTest extends ErpTest{
    @Autowired
    private DeliveryService deliveryService;

    @Test
    @Rollback(false)
    public void insertDeliveryTest() {
        Delivery delivery = new Delivery();
        delivery.setName("顺风");
        delivery.setLaw(1);
        delivery.setPrintHtml("1321321");
        delivery.setLogisticsPicturePath("1231232");
        delivery.setCreateTime(DateUtils.getCurrentDate());
        int count=deliveryService.saveDelivery(delivery);
        System.out.println("返回值"+count+delivery);
        Assert.assertEquals("测试失败", 1,count);
    }

    @Test
    @Rollback(false)
    public void updateDeliveryTest() {
        Delivery delivery = new Delivery();
        delivery.setId(93);
        delivery.setName("大风dj");
        delivery.setLaw(1);
        delivery.setPrintHtml("1321321");
        delivery.setLogisticsPicturePath("1231232");
        int count=deliveryService.updateDelivery(delivery);
        Assert.assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void deleteDeliveryTest() {
        int [] idArray={1};
        int count=deliveryService.deleteDelivery(idArray);
        assertEquals("测试失败",2,count);
    }

    @Test
    @Rollback(false)
    public void findDeliveryAllTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       DeliveryQuery deliveryQuery=new DeliveryQuery();
//        deliveryQuery.setId(29);
       System.out.println( deliveryService.findDeliveryAll(deliveryQuery));
    }


}

