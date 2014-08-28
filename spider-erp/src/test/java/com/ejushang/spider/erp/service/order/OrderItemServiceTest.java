package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.OrderItem;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.OrderItemQuery;
import com.ejushang.spider.service.order.IOrderItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午5:49
 */
public class OrderItemServiceTest extends ErpTest {

    @Autowired
    private IOrderItemService orderItemService;

    /**
     * 订单项新增
     */
    @Test
    @Rollback(false)
    public void testSaveOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo("测试12345");
        orderItem.setProdId(223348);
        orderItem.setProdCode("测试产品编号123");
        orderItem.setSkuCode("测试SKU编号234");
        orderItem.setProdName("测试商品名称222");
        orderItem.setProdPrice(1000L);
        orderItem.setProdCount(8);
        orderItem.setTotalFee(8000L);
        orderItem.setActualFee(7200L);
        orderItem.setOutOrderNo("测试外部订单号");
        orderItem.setItemType("单个商品");
        orderItem.setPriceDescription("不打折");

        int count = orderItemService.saveOrderItem(orderItem);

        assertThat(count,is(equalTo(1)));
    }
    /**
     *
     */
    @Test
    @Rollback(false)
    public void findOrderItemsByOrderNo(){


//       orderItemService.findOrderItemsByOrderNo("1002");
        Integer id=329;
          orderItemService.deleteOrderItemById(id);
    }

    /**
     * 订单项更新
     */
    @Test
    @Rollback(false)
    public void testUpdateOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo("测试12345");
        //orderItem.setProdId(223346);
        //orderItem.setProdCode("测试产品编号123");
        //.setSkuCode("测试SKU编号234");
        orderItem.setProdName("测试商品名称333");
        //orderItem.setProdPrice(1000L);
        //orderItem.setProdCount(8);
        //orderItem.setTotalFee(8000L);
        //orderItem.setActualFee(7200L);
        //orderItem.setOutOrderNo("测试外部订单号");
        //orderItem.setItemType("单个商品");
        //orderItem.setPriceDescription("不打折");

//        int count = orderItemService.updateOrderItem(orderItem);

//        assertThat(count,is(equalTo(4)));
    }

    /**
     * 订单项删除
     */
    @Test
    @Rollback(false)
    public void testDeleteOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(2);
        int count = orderItemService.deleteOrderItem(orderItem);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 订单项查询
     */
    @Test
    @Rollback(false)
    public void testFindOrderItems(){
        OrderItem orderItem = new OrderItem();
        List<OrderItem> orderItems = orderItemService.findOrderItems(orderItem);

        assertThat(orderItems,notNullValue());
    }

    @Test
    @Rollback(false)
    public void testupdate(){
        OrderItem orderItem = new OrderItem();
//        orderItem.setId(270);
//        System.out.println(orderItemService.deleteOrderItemjById(270));
        OrderItemQuery orderItemQuery=new OrderItemQuery();

//        orderItemQuery.setId(263);
//        orderItemQuery.setProdCount(10);

        orderItemQuery.setId(2137);

        orderItemQuery.setPostFee("30.00");
        System.out.println(orderItemService.updateOrderItem(orderItemQuery));

    }

}
