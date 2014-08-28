package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.domain.OriginalOrderItem;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.service.order.IOriginalOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午11:49
 */
public class OriginalOrderServiceTest extends ErpTest {

    @Autowired
    private IOriginalOrderService originalOrderService;

    /**
     * 原始订单新增
     */
    @Test
    @Rollback(false)
    public void testSaveOriginalOrder(){
        OriginalOrder originalOrder = new OriginalOrder();
        originalOrder.setStatus("测试status3");
        originalOrder.setTotalFee(3000L);
        originalOrder.setBuyerId("测试买家id002");
        originalOrder.setReceiverName("测试收货人姓名001");
        originalOrder.setReceiverState("广东省");
        originalOrder.setReceiverCity("深圳市");
        originalOrder.setReceiverAddress("88路88号");
        originalOrder.setOutPlatformType("天猫");
        originalOrder.setOutOrderNo("测试222");
        originalOrder.setShopId(9999L);
        originalOrder.setShopName("测试店铺332");

        int count = originalOrderService.saveOriginalOrder(originalOrder);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 原始订单更新
     */
    @Test
    @Rollback(false)
    public void testUpdateOriginalOrder(){
        OriginalOrder originalOrder = new OriginalOrder();
        originalOrder.setId(2);
        //originalOrder.setStatus("测试status");
        originalOrder.setTotalFee(3800L);
        //originalOrder.setBuyerId("测试买家id001");
        originalOrder.setReceiverName("测试收货人姓名001");
        // originalOrder.setReceiverState("广东省");
        // originalOrder.setReceiverCity("深圳市");
        // originalOrder.setReceiverAddress("88路88号");
        // originalOrder.setOutPlatformType("天猫");
        // originalOrder.setOutOrderNo("测试222");
        // originalOrder.setShopId(9999);
        // originalOrder.setShopName("测试店铺332");

        int count = originalOrderService.updateOriginalOrder(originalOrder);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 原始订单更新
     */
    @Test
    @Rollback(false)
    public void testDeleteOriginalOrder(){
        OriginalOrder originalOrder = new OriginalOrder();
        originalOrder.setId(2);
        int count = originalOrderService.deleteOriginalOrder(originalOrder);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 原始订单查询
     */
    @Test
    @Rollback(false)
    public void testFindOriginalOrders(){
        List<OriginalOrder> originalOrders = originalOrderService.findOriginalOrders(false);

        assertThat(originalOrders, notNullValue());
        for(OriginalOrder originalOrder : originalOrders) {
            assertThat(originalOrder.getOutOrderNo(), notNullValue());
            List<OriginalOrderItem> originalOrderItems = originalOrder.getOriginalOrderItems();
            assertThat(originalOrderItems.isEmpty(), is(false));
            for(OriginalOrderItem originalOrderItem : originalOrderItems) {
                assertThat(originalOrderItem.getOrderNo(), notNullValue());
            }
        }
    }


}
