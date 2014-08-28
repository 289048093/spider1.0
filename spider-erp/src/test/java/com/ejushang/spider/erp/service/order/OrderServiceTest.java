package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OrderItem;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.exception.GenerateException;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.service.order.IOrderItemService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.AddOrderVo;
import com.ejushang.spider.vo.OrderVo;
import com.ejushang.spider.vo.QueryProdVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午2:59
 */
public class OrderServiceTest extends ErpTest {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    /**
     * 订单新增
     */
    @Test
    @Rollback(false)
    public void testSaveOrder() {
        int orderNo = 100000;

        for (int i = 0; i < 2000; i++){
            Order order = new Order();
            order.setOrderNo(""+(orderNo++));
            order.setOrderType("NORMAL");
            order.setOrderStatus("WAIT_PROCESS");
            order.setBuyerId("testbuyer");
            order.setBuyerMessage("测试打印数据");
            order.setRemark("测试打印");
            order.setTotalFee(10L);
            order.setReceiverName("郭溪");
            order.setReceiverState("四川省");
            order.setReceiverCity("成都市");
            order.setReceiverDistrict("郫县");
            order.setReceiverAddress("四川传媒学院");
            order.setReceiverMobile("13600000000");
            order.setShippingComp("shentong");
            order.setReceiverZip("300302");
            order.setRepoId(10);
            order.setRepoName("戴德惠州仓");
            order.setBuyTime(new Date());
            order.setPayTime(new Date());
            order.setOutPlatformType("taobao");
            order.setOutOrderNo("548528818624783");
            order.setShopId(104284965);
            order.setShopName("易居尚官方旗舰店");

            int count = orderService.createOrder(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setOrderNo(order.getOrderNo());
            orderItem.setProdId(20706);
            orderItem.setProdCode("SY0001");
            orderItem.setSkuCode("SY0001");
            orderItem.setProdName("唐采招财猫饭碗 陶瓷碗 套装 5件套");
            orderItem.setProdPrice(58800L);
            orderItem.setProdCount(1);
            orderItem.setTotalFee(58800L);
            orderItem.setActualFee(10000L);
            orderItem.setOutOrderNo("548528818624783");
            orderItem.setItemType("PRODUCT");
            orderItem.setSubOutOrderNo("548528818624783");

            orderItemService.saveOrderItem(orderItem);

        }


        //assertThat(count, is(equalTo(1)));


    }

    /**
     * 订单更新
     */
    @Test
    @Rollback(false)
    public void testUpdateOrder() {
        Order order = new Order();
        order.setOrderNo("测试124");
        order.setOrderType("测试type33");
        order.setOrderStatus("测试status");
        order.setBuyerId("测试买家id");
        order.setTotalFee(0L);
        order.setReceiverName("张三");
        order.setReceiverState("广东省");
        order.setReceiverCity("深圳市");
        order.setReceiverAddress("88路88号");
        order.setRepoId(8888);
        order.setRepoName("测试库房");
        order.setOutPlatformType("天猫");
        order.setOutOrderNo("测试外部订单号");
        order.setShopId(9999);
        order.setShopName("测试店铺名称");
//        int count = orderService.updateOrder(order);
//
//        assertThat(count, is(equalTo(1)));
    }

    /**
     * 订单删除
     */
    @Test
    @Rollback(false)
    public void testDeleteOrder() {
        Order order = new Order();
        order.setOrderNo("测试124");
        int count = orderService.deleteOrder(order);

        assertThat(count, is(equalTo(1)));
    }

    @Test
    @Rollback(false)
    public void testFindOrderDetail() throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

        OrderQuery orderQuery = new OrderQuery();


         Page page=new Page();

        List<OrderVo> or = new ArrayList<OrderVo>();
        page = orderService.findDetailOrders(orderQuery);




    }
       @Test
       @Rollback(false)
       public void merage(){
           String or="9,8";
           orderService.mergeOrder(or);


       }
//    @Test
//    @Rollback(false)
//    public void testUpdateOrderBy() throws ParseException {
//
//        Order order = new Order();
//
//        order.setRepoId(11);
//        order.setShippingComp("shunfeng");
//
//        Integer[] orderNos = {1, 8, 9, 10};
//        orderService.updateOrderStatusByOrder(order, orderNos);
//
//    }


    /**
     * 订单查询
     */
    @Test
    @Rollback(false)
    public void testFindOrders() throws ParseException {

        orderService.findOrders();
    }

    @Test
    @Rollback(false)
    public void updateOrderShipping() throws GenerateException, IOException {
        int[] ids = {1105,1357,1351,1350,1347};
        String shippingComp = "shunfeng";
        String intNo = "120000000000";
        String isCover = "k";
        orderService.updateOrderShipping(ids, shippingComp, intNo, isCover);

    }


    @Test
    @Rollback(false)
    public void test() throws OrderAnalyzeException {
        int[] ids = {35, 36};
        orderService.orderByOrderNo(ids, "115");
    }

    @Test
    public void testDeliveryPrint() {
        Integer[] orderIds = {1};
        orderService.deliveryPrint(orderIds);
    }

    @Test
    public void testOrderPrint() {
        Integer[] orderIds = {899};
        orderService.orderPrint(orderIds);
    }

    @Test
    public void testOrderInspection() {
        String[] shippingNos = {"1000001"};
        String[] shippingIds = {"1"};
        orderService.orderInspection(shippingIds);
    }

    @Test
    @Rollback(false)
    public void testAddOrder() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        QueryProdVo[] queryProdVos = null;
        AddOrderVo addOrderVo = new AddOrderVo();
        addOrderVo.setBuyerId("111");
        addOrderVo.setBuyerMessage("111");
        addOrderVo.setReceiverAddress("111");
        addOrderVo.setReceiverCity("111");
        addOrderVo.setReceiverDistrict("111");
        addOrderVo.setReceiverName("111");
        addOrderVo.setReceiverMobile("111");
        addOrderVo.setReceiverPhone("111");
        addOrderVo.setReceiverState("111");
        addOrderVo.setReceiverZip("111");
        addOrderVo.setRemark("1110");
        addOrderVo.setShippingComp("111");
        addOrderVo.setShopId(111);
        addOrderVo.setOrderType("111");
       // addOrderVo.setTotalFee(11L);
        // Order order= orderService.addOrderByHand(addOrderVo,queryProdVos,);
        //  System.out.println(order.toString());
        /**
         * orderItemType:PRODUCT
         queryProdVos:{"autoId":null,"prodNo":"DD005","prodName":"\u6234\u5fb7\u7231\u795e16cm\u5355\u67c4\u6c64\u9505","prodCode":"4895161310145","count":1}
         */
        String[] shippingNos = {"1131"};
        orderService.orderInspection(shippingNos);
    }

    @Test
    public void testOrderUpdateStatus() {
        Integer[] orderIds = {1094};
        String status = "PRINTED";
        //Integer count = orderService.orderUpdateStatus(orderIds, status);
       // System.out.println(count);
    }

    @Test
    public void testOrderCancellation() {
        Integer[] orderIds = {1095};
        try {
            Integer count = orderService.orderCancellation(orderIds);
            System.out.println(count);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testOrderRecover(){
        Integer[] orderIds = {1095};
        try{
            Integer count = orderService.orderRecover(orderIds);
            System.out.println(count);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOrderConfirm() {
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderConfirm(orderIds);

        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testOrderAffirmPrint() {
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderAffirmPrint(orderIds);
            System.out.println(count);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOrderBackToConfirm() {
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderBackToConfirm(orderIds);
            System.out.println(count);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOrderBatchExamine() {
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderBatchExamine(orderIds);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOrderBackToPrint(){
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderBackToPrint(orderIds);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOrderInvoice(){
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderInvoice(orderIds);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOrderBackToWaitProcess(){
        Integer[] orderIds = {1093};
        try {
            Integer count = orderService.orderBackToWaitProcess(orderIds);
        } catch (ErpBusinessException e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void te(){
        Order order=new Order();
        order.setId(1133);
        order.setShippingNo("100000000000");
        System.out.println(orderService.updateOrderByOrder(order));
    }
}

