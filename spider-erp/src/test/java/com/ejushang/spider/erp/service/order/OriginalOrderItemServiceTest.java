package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.OriginalOrderItem;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.service.order.IOriginalOrderItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午11:49
 */
public class OriginalOrderItemServiceTest extends ErpTest {

    @Autowired
    private IOriginalOrderItemService originalOrderItemService;

    /**
     * 原始订单项新增
     */
    @Test
    @Rollback(false)
    public void testSaveOriginalOrderItem(){
        OriginalOrderItem originalOrderItem = new OriginalOrderItem();
        originalOrderItem.setOrderNo("测试222");
        originalOrderItem.setSkuCode("测试SKUCODE003");
        originalOrderItem.setPrice(4000L);
        originalOrderItem.setBuyCount(5L);
        originalOrderItem.setTotalFee(20000L);
        originalOrderItem.setActualFee(16000L);

        int count = originalOrderItemService.saveOriginalOrderItem(originalOrderItem);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 原始订单项更新
     */
    @Test
    @Rollback(false)
    public void testUpdateOriginalOrderItem(){
        OriginalOrderItem originalOrderItem = new OriginalOrderItem();
        originalOrderItem.setId(2);
        originalOrderItem.setSkuCode("测试SKUCODE004");

        int count = originalOrderItemService.updateOriginalOrderItem(originalOrderItem);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 原始订单项更新
     */
    @Test
    @Rollback(false)
    public void testDeleteOriginalOrderItem(){
        OriginalOrderItem originalOrderItem = new OriginalOrderItem();
        originalOrderItem.setId(2);
        int count = originalOrderItemService.deleteOriginalOrderItem(originalOrderItem);

        assertThat(count,is(equalTo(1)));
    }


}
