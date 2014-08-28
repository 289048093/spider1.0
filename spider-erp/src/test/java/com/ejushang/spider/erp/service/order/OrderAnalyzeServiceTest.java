package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.bean.TransformOrderResultBean;
import com.ejushang.spider.constant.OrderItemType;
import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.main.analyze.OrderAnalyzer;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.service.order.IOrderAnalyzeService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.service.order.IOriginalOrderService;
import com.ejushang.spider.service.product.IMealSetService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.vo.QueryProdVo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * User: liubin
 * Date: 14-1-6
 */
public class OrderAnalyzeServiceTest extends OrderAnalyzeBaseTest {

    @Autowired
    private IOriginalOrderService originalOrderService;
    @Autowired
    private IMealSetService mealsetService;
    @Autowired
    private OrderAnalyzer orderAnalyzer;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private IOrderAnalyzeService orderAnalyzeService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IProductService productService;


    @Test
    @Rollback(false)
    public void testTransformToOrderSuccess() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        List<OriginalOrder> originalOrders = prepareOriginalOrders(null);

        TransformOrderResultBean transformOrderResultBean = orderAnalyzeService.transformToOrder(originalOrders);
        List<OriginalOrder> errorOriginalOrders = transformOrderResultBean.getErrorOriginalOrders();
        //应该有一个错误原始订单(sku不存在)
        assertThat(errorOriginalOrders.size(), is(1));
        List<Order> results = transformOrderResultBean.getResults();
        //校验正确的订单数量是否符合预期
        assertThat(results.size(), is(originalOrders.size() - 1));
        int originalOrderItemSize = 0;
        int orderItemSize = 0;
        for(OriginalOrder originalOrder : originalOrders) {
            boolean error = false;
            for(OriginalOrder errOriginalOrder : errorOriginalOrders) {
                if(errOriginalOrder == originalOrder) {
                    error = true;
                    break;
                }
            }
            if(error) continue;
            originalOrderItemSize += originalOrder.getOriginalOrderItems().size();
        }
        for(Order order : results) {
            orderItemSize += order.getOrderItemList().size();
        }
        //校验订单项数量正确
        assertThat(originalOrderItemSize, not(0));
        assertThat(orderItemSize, not(0));

        //使用原始订单项sku校验转化后的结果是否正确
        for(OriginalOrder originalOrder : originalOrders) {
            String outOrderNo = originalOrder.getOutOrderNo();
            for(Order order : results) {
                if(!order.getOutOrderNo().equals(outOrderNo)) continue;
                List<OriginalOrderItem> originalOrderItems = originalOrder.getOriginalOrderItems();
                List<OrderItem> orderItemList = order.getOrderItemList();
                for(OriginalOrderItem originalOrderItem : originalOrderItems) {
                    List<OrderItem> generatedOrderItemList = new ArrayList<OrderItem>();
                    for(OrderItem orderItem : orderItemList) {
                        if(orderItem.getSubOutOrderNo().equals(originalOrderItem.getSubOrderNo())) {
                            generatedOrderItemList.add(orderItem);
                        }
                    }

                    String sku = originalOrderItem.getSkuCode();
                    if(sku.equals(postageProduct.getProdCode())) {
                        //该原始订单项是虚拟邮费产品,则生成的订单中不应该有该产品
                        assertThat(generatedOrderItemList.size(), is(0));
                        continue;
                    }
                    Product product = productService.findProductBySKU(sku, ProductType.PRODUCT.toString());
                    if(product != null) {
                        //该原始订单项对应的是产品
                        assertThat(generatedOrderItemList.size(), is(1));
                        OrderItem orderItem = generatedOrderItemList.get(0);
                        assertThat(orderItem.getItemType(), is(OrderItemType.PRODUCT.toString()));
                        continue;
                    }
                    Mealset mealset = mealsetService.findMealsetBySKU(sku);
                    if(mealset != null) {
                        List<MealsetItem> mealsetItems = mealset.getMealsetItemList();
                        //此时应该是套餐sku
                        assertThat(generatedOrderItemList.size(), not(0));
                        assertThat(generatedOrderItemList.size(), is(mealsetItems.size()));
                        for(OrderItem orderItem : generatedOrderItemList) {
                            assertThat(orderItem.getItemType(), is(OrderItemType.MEALSET.toString()));
                        }
                        continue;
                    }

                    throw new AssertionError(String.format("originalOrderItem[outOrderNo=%s]的sku[%s]没有在生成的Order中找到对应的OrderItem", outOrderNo, sku));
                }

            }
        }



    }

    @Test
    @Rollback(false)
    public void testPrepareImportSuccess() throws OrderAnalyzeException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        //预期会有3个赠品项
//        int expectedGiftProductItemCount = 3;
//        List<Object> giftParamList = new ArrayList<Object>();
//
//        List<OriginalOrder> originalOrders = prepareOriginalOrders(giftParamList);

        List<OriginalOrder> originalOrders = prepareOriginalOrders(null);

        TransformOrderResultBean transformOrderResultBean = orderAnalyzeService.transformToOrder(originalOrders);
        List<Order> results = transformOrderResultBean.getResults();

        //记录处理前的订单明细
        List<OrderItem> allOrderItemsBefore = new ArrayList<OrderItem>();
        for(Order order : results) {
            allOrderItemsBefore.addAll(order.getOrderItemList());
        }

        //处理订单
        List<Order> orders = orderAnalyzeService.prepareImport(results);

        //处理过后的订单数量要比处理前的多(因为拆单)
        assertThat(orders.size(), greaterThan(results.size()));

        //记录处理后的订单明细
        List<OrderItem> allOrderItemsAfter = new ArrayList<OrderItem>();
        for(Order order : orders) {
            allOrderItemsAfter.addAll(order.getOrderItemList());
        }
        //校验订单项数量正确
        int orderItemSizeBefore = allOrderItemsBefore.size();
        int orderItemSizeAfter = allOrderItemsAfter.size();
        assertThat(orderItemSizeBefore, not(0));
        assertThat(orderItemSizeAfter, not(0));
        assertThat(orderItemSizeAfter, is(orderItemSizeBefore));
//        assertThat(orderItemSizeAfter, is(orderItemSizeBefore + expectedGiftProductItemCount));

        Map<Long, Set<String>> productIdAndTypeMap = new HashMap<Long, Set<String>>();
        for(OrderItem orderItemBefore : allOrderItemsBefore) {
            Set<String> orderItemTypeSet = productIdAndTypeMap.get(orderItemBefore.getProdId());
            if(orderItemTypeSet == null) {
                orderItemTypeSet = new HashSet<String>();
                productIdAndTypeMap.put(orderItemBefore.getProdId().longValue(), orderItemTypeSet);
            } else {
                assertThat(String.format("发现productId[%d]拥有重复的itemType[%s]", orderItemBefore.getProdId(), orderItemBefore.getItemType()),
                        orderItemTypeSet.contains(orderItemBefore.getItemType()), is(false));
            }
            orderItemTypeSet.add(orderItemBefore.getItemType());
        }

//        //计算赠品数量
//        List<Product> giftProducts = (List<Product>) giftParamList.get(0);
//        Integer brandSellProductId = giftProducts.get(0).getId();
//        Integer prodSellProductId = giftProducts.get(1).getId();
//        int brandSellProductCount = 0;
//        int prodSellProductCount = 0;

        //校验处理后的订单项
        for(OrderItem orderItemAfter : allOrderItemsAfter) {
            if(OrderItemType.GIFT.toString().equals(orderItemAfter.getItemType())) {
//                //校验赠品添加是否正确
//                if(brandSellProductId.equals(orderItemAfter.getProdId())) {
//                    brandSellProductCount ++;
//                } else if(prodSellProductId.equals(orderItemAfter.getProdId())) {
//                    prodSellProductCount ++;
//                } else {
//                    throw new AssertionError(String.format("???混入了不明赠品,productId[%d]", orderItemAfter.getProdId()));
//                }
            } else {
                //校验该订单项是否在处理前的订单出现过
                Set<String> orderItemTypeSet = productIdAndTypeMap.get(orderItemAfter.getProdId().longValue());
                assertThat(String.format("处理后的订单中productId[%d],itemType[%s]的订单项没有在处理前的订单项中找到", orderItemAfter.getProdId(), orderItemAfter.getItemType()),
                        orderItemTypeSet, allOf(notNullValue(), contains(orderItemAfter.getItemType())));
            }
        }

//        //校验赠品数量正确
//        assertThat(brandSellProductCount, is(2));
//        assertThat(prodSellProductCount, is(1));
    }

    @Test
    @Rollback(false)
    public void testSaveAnalyzeResultSuccess() throws OrderAnalyzeException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

//        List<Object> giftParamList = new ArrayList<Object>();
//        List<OriginalOrder> originalOrders = prepareOriginalOrders(giftParamList);
        List<OriginalOrder> originalOrders = prepareOriginalOrders(null);

        //用来拷贝多个原始订单,测试多个属性完全一样,只是备注不同的原始订单导入是否能正常工作
        OriginalOrder useForCopy = originalOrders.get(originalOrders.size() - 1);

        int copyCount = 3;
        //复制原始订单
        List<OriginalOrder> sameOriginalOrders = prepareSameOriginalOrdersForRemarkUpdateTest(useForCopy, copyCount);

        originalOrders.addAll(sameOriginalOrders);

        List<Order> orders = orderAnalyzer.analyzeOriginalOrders(originalOrders);
        int generatedOrderSizeBefore = orders.size();

        assertSaveAnalyzeResultSuccess(originalOrders, orders);


        //校验多个outOrderNo相同的原始订单是否能正常导入
        int useForCopyOutOrderNoOccurTimes = 0; //用来copy的原始订单的outOrderNo出现次数
        String useForCopyOriginalOrderRemark = null;    //最后保存的备注
        for(Order order : orders) {
            if(order.getOutOrderNo().equals(useForCopy.getOutOrderNo())) {
                if(useForCopyOriginalOrderRemark == null) {
                    useForCopyOriginalOrderRemark = order.getRemark();
                    useForCopyOutOrderNoOccurTimes++;
                } else {
                    if(!useForCopyOriginalOrderRemark.equals(order.getRemark())) {
                        //如果原始订单号相同,但备注不同,代表复制的原始订单不止一个最后生效了
                        useForCopyOutOrderNoOccurTimes++;
                    }
                }
            }
        }
        assertThat(useForCopyOutOrderNoOccurTimes, is(1));  //生成的订单中,用来复制的原始订单的外部订单号只出现过1次
        assertThat(useForCopyOriginalOrderRemark, notNullValue());
        assertThat(useForCopyOriginalOrderRemark, is(REMARK_START_WORD + copyCount));  //保留的是最后一个原始订单的备注

        //现在测试如果有相同的外部订单号在数据库了,只修改备注的原始订单是否能做到只更新数据库中的订单备注
        copyCount = 5;
        originalOrders = prepareOriginalOrders(null);
        List<OriginalOrder> allOriginalOrders = new ArrayList<OriginalOrder>(originalOrders);
        sameOriginalOrders = prepareSameOriginalOrdersForRemarkUpdateTest(useForCopy, copyCount);
        allOriginalOrders.addAll(sameOriginalOrders);
        orders = orderAnalyzer.analyzeOriginalOrders(allOriginalOrders);
        assertThat(orders.size(), is(generatedOrderSizeBefore));
        assertSaveAnalyzeResultSuccess(originalOrders, orders);

        //根据外部订单号从数据库中查询订单,校验备注已经修改
        List<Order> generatedByUseForCopyOrders = orderService.findOrderByOutOrderNo(useForCopy.getOutOrderNo());
        assertThat(generatedByUseForCopyOrders.isEmpty(), is(false));
        for(Order order : generatedByUseForCopyOrders) {
            assertThat(order.getRemark(), is(REMARK_START_WORD + copyCount));
        }


    }

    @Test
    @Rollback(false)
    @Ignore
    public void testAddGiftByHand(){

     //  QueryProdVo[] queryProdVos=[{111,111,111,1}];
      // orderAnalyzeService.addGiftByHand(prodCodes,1,"1002",OrderItemType.GIFT);
       List<OrderItem> orderItems=orderService.findOrderByOrderNo("1002").getOrderItemList();

   }


}
