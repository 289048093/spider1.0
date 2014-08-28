package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.constant.OrderStatus;
import com.ejushang.spider.domain.Order;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.service.order.IOrderFlowService;
import com.ejushang.spider.service.order.IOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * User: liubin
 * Date: 14-1-6
 */
public class OrderFlowServiceTest extends ErpTest {

    @Autowired
    private IOrderFlowService orderFlowService;
    @Autowired
    private IOrderService orderService;


    @Test
    @Rollback(false)
    public void testChangeStateSuccess() {
        assertOrderStatus(OrderStatus.WAIT_PROCESS, OrderStatus.INVALID, 1, true);
        assertOrderStatus(OrderStatus.INVALID, OrderStatus.WAIT_PROCESS, 1, true);

        assertOrderStatus(OrderStatus.WAIT_PROCESS, OrderStatus.CONFIRMED, 1, true);
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.PRINTED, 1, true);
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.EXAMINED, 1, true);
        assertOrderStatus(OrderStatus.EXAMINED, OrderStatus.INVOICED, 1, true);

        assertOrderStatus(OrderStatus.EXAMINED, OrderStatus.PRINTED, 1, true);
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.CONFIRMED, 1, true);
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.WAIT_PROCESS, 1, true);

        assertOrderStatus(OrderStatus.WAIT_PROCESS, OrderStatus.CONFIRMED, 1, false);
        assertOrderStatus(OrderStatus.WAIT_PROCESS, OrderStatus.INVALID, 1, false);
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.PRINTED, 1, false);
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.EXAMINED, 2, false);
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.INVOICED, 3, false);
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.EXAMINED, 1, false);
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.INVOICED, 2, false);
        assertOrderStatus(OrderStatus.EXAMINED, OrderStatus.INVOICED, 1, false);

        assertOrderStatus(OrderStatus.EXAMINED, OrderStatus.PRINTED, 1, false);
        assertOrderStatus(OrderStatus.EXAMINED, OrderStatus.CONFIRMED, 2, false);
        assertOrderStatus(OrderStatus.EXAMINED, OrderStatus.WAIT_PROCESS, 3, false);
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.CONFIRMED, 1, false);
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.WAIT_PROCESS, 2, false);
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.WAIT_PROCESS, 1, false);

    }

    @Test(expected = ErpBusinessException.class)
    public void testChangeWaitProcessToPrintedFail() {
        assertOrderStatus(OrderStatus.WAIT_PROCESS, OrderStatus.PRINTED, 2, true);
    }

    @Test(expected = ErpBusinessException.class)
    public void testChangeWaitProcessToPrintedFail2() {
        assertOrderStatus(OrderStatus.WAIT_PROCESS, OrderStatus.PRINTED, 2, false);
    }

    @Test(expected = ErpBusinessException.class)
    public void testChangeConfirmedToExaminedFail() {
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.EXAMINED, 2, true);
    }

    @Test(expected = ErpBusinessException.class)
    public void testChangePrintedToWaitProcessFail() {
        assertOrderStatus(OrderStatus.PRINTED, OrderStatus.WAIT_PROCESS, 2, true);
    }

    @Test(expected = ErpBusinessException.class)
    public void testChangeConfirmedToInvalidFail() {
        assertOrderStatus(OrderStatus.CONFIRMED, OrderStatus.INVALID, 2, false);
    }

    @Test(expected = ErpBusinessException.class)
    public void testChangeInvoiceToExaminedFail() {
        assertOrderStatus(OrderStatus.INVOICED, OrderStatus.EXAMINED, 1, false);
    }

    private Order newOrder() {
        Order order = new Order();
        order.setId(0);
        return order;
    }

    private void assertOrderStatus(OrderStatus from, OrderStatus to, int callCount, boolean strict) {
        Order order = newOrder();
        order.setOrderStatus(from.toString());
        orderFlowService.changeStatus(order, from, to, strict);
        assertThat(order.getOrderStatus(), is(to.toString()));
        assertThat(order.getId(), is(callCount));

    }

    //测试方法
//    @Override
//    @Transactional
//    public void doConfirm(Order order) {
//        System.out.println("doConfirm");
//        order.setOrderStatus(OrderStatus.CONFIRMED.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void cancelConfirm(Order order) {
//        System.out.println("cancelConfirm");
//        order.setOrderStatus(OrderStatus.WAIT_PROCESS.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void doPrint(Order order) {
//        System.out.println("doPrint");
//        order.setOrderStatus(OrderStatus.PRINTED.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void cancelPrint(Order order) {
//        System.out.println("cancelPrint");
//        order.setOrderStatus(OrderStatus.CONFIRMED.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void doExamine(Order order) {
//        System.out.println("doExamine");
//        order.setOrderStatus(OrderStatus.EXAMINED.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void cancelExamine(Order order) {
//        System.out.println("cancelExamine");
//        order.setOrderStatus(OrderStatus.PRINTED.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void doInvoice(Order order) {
//        System.out.println("doInvoice");
//        order.setOrderStatus(OrderStatus.INVOICED.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void cancelInvoice(Order order) {
//        throw new UnsupportedOperationException("不能取消发货");
//    }
//
//    @Override
//    @Transactional
//    public void doInvalid(Order order) {
//        System.out.println("doInvalid");
//        order.setOrderStatus(OrderStatus.INVALID.toString());
//        order.setId(order.getId() + 1);
//    }
//
//    @Override
//    @Transactional
//    public void cancelInvalid(Order order) {
//        System.out.println("cancelInvalid");
//        order.setOrderStatus(OrderStatus.WAIT_PROCESS.toString());
//        order.setId(order.getId() + 1);
//    }

}
