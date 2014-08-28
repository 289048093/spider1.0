package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.OrderFetchQuery;
import com.ejushang.spider.service.order.IOrderFetchService;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.util.DateUtils;
import com.ejushang.spider.util.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午5:49
 */
public class OrderFetchServiceTest extends ErpTest {

    @Autowired
    private IOrderFetchService orderFetchService;

    @Autowired
    private IShopService shopService;

    /**
     * 订单抓取新增
     */
    @Test
    @Rollback(false)
    public void testSaveOrderFetch(){
        OrderFetch orderFetch = new OrderFetch();
        orderFetch.setFetchTime(DateUtils.getCurrentDate());
        orderFetch.setOutPlatform("天猫");
        orderFetch.setShopId("9999");

        int count = orderFetchService.saveOrderFetch(orderFetch);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 订单抓取更新
     */
    @Test
    @Rollback(false)
    public void testUpdateOrderFetch(){
        OrderFetch orderFetch = new OrderFetch();
        orderFetch.setId(2);
        orderFetch.setOutPlatform("京东");
        int count = orderFetchService.updateOrderFetch(orderFetch);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 订单抓取删除
     */
    @Test
    @Rollback(false)
    public void testDeleteOrderFetch(){
        OrderFetch orderFetch = new OrderFetch();
        orderFetch.setId(2);
        int count = orderFetchService.deleteOrderFetch(orderFetch);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 订单抓取查询
     */
    @Test
    @Rollback(false)
    public void testFindOrderFetchs() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        OrderFetchQuery orderFetchQuery = new OrderFetchQuery();
        Page page = orderFetchService.findOrderFetchs(orderFetchQuery);

        assertThat(page,notNullValue());
    }

    @Test
    @Rollback(false)
    public void testFindLastOrderFetch(){
        List<ShopBean> shopBeanList = shopService.findAllShopBean();
        for(ShopBean shopBean : shopBeanList){
            OrderFetch orderFetch = orderFetchService.findLastOrderFetch(shopBean.getOutPlatformType(),shopBean.getShopId());
            System.out.println(orderFetch);
        }
    }
}
