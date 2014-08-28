package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.exception.GenerateException;
import com.ejushang.spider.query.OrderItemQuery;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.service.order.IOrderAnalyzeService;
import com.ejushang.spider.service.order.IOrderItemService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.util.JsonUtil;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.*;
import com.ejushang.spider.web.util.JsonResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Json.zhu
 * Date: 13-12-26
 * Time: 上午10:17
 * 订单管理
 */

@Controller
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IOrderAnalyzeService orderAnalyzeService;
    @Autowired
    private IShopService shopService;


    /**
     * 通过orderQuery对象查询order
     *
     * @param orderQuery 查询对象
     * @param response   响应对象
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findOrders(OrderQuery orderQuery, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController findOrders 传值对象OrderQuery{" + orderQuery.toString() + "}");
        }

      Page page = orderService.findDetailOrders(orderQuery);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }
    /**
     * 通过order订单的ID数组合并订单
     */
    @RequestMapping("/merge")
    public void mergeOrders(String orderIds,HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController mergeOrders 传值对象orderIds字符串ID{" + orderIds + "}");
        }
        int check=orderService.mergeOrder(orderIds);
        if(check==0){
            new JsonResult(JsonResult.FAILURE,"勾选至少2个订单项").toJson(response);
        }
        else if(check==1){
            new JsonResult(JsonResult.SUCCESS,"订单合并成功").toJson(response);
        }
        else if(check==2){
            new JsonResult(JsonResult.FAILURE,"勾选的订单条件不在同一仓库或者不符合要求").toJson(response);
        }




    }

    /**
     * 通过orderNo订单编号查询orderItem
     *
     * @param orderIds 订单ID
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping("/ItemList")
    public void findOrderItems(String orderIds, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController findOrderItems 传值orderId{" + orderIds + "}");
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST, orderItemService.findOrderItemsByOrderId(Integer.parseInt(orderIds))).toJson(response);
    }

    /**
     * 对order订单拆分
     *
     * @param ids      order订单ID数组
     * @param orderNo  订单编号
     * @param response 响应对象
     * @throws IOException 流异常
     */
    @RequestMapping("/orderSeparate")
    public void separateOrderByHand(int[] ids, String orderNo, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController orderSeparate 传值ids[]{" + ids + "},String类型orderNo{" + orderNo + "}");
        }
        try {
            String addOrderNo = orderService.orderByOrderNo(ids, orderNo);

            if (log.isInfoEnabled()) {
                log.info("拆分后的订单为：" + orderService.findOrderByOrderNo(orderNo).getTotalFee());
            }
            new JsonResult(JsonResult.SUCCESS, "拆分成功！拆分出来的订单号为：" + addOrderNo).toJson(response);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("拆分失败", e);
            }
            new JsonResult(JsonResult.FAILURE, "拆分失败！" + e.getMessage()).toJson(response);
        }
    }

    /**
     * 更新订单：通过order更新order
     *
     * @param data
     * @param response
     * @throws IOException
     */
    @RequestMapping({"/updateByOrder", "/updateShippingNo"})
    public void updateOrderByOrder(String data, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController updateByOrder 传值data{" + data + "}");
        }
        Order order = JsonUtil.json2Object(data, Order.class);
        Page page = new Page();
        int check = orderService.updateOrderByOrder(order);
        if (check == 0) {
            new JsonResult(JsonResult.FAILURE, "更新失败").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
        } else if (check == 1) {
            new JsonResult(JsonResult.SUCCESS, "更新成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
        } else if (check == 2) {
            new JsonResult(JsonResult.FAILURE, "有重复物流单号").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
        } else if (check == 3) {
            new JsonResult(JsonResult.FAILURE, "物流单号必须是数字").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
        }
    }

    /**
     * 更新订单：通过order更新order
     *
     * @param data
     * @param response
     * @throws IOException
     */
    @RequestMapping("/updateOrderItemByOrderItem")
    public void updateOrderItemByOrderItem(String data, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController updateOrderItemByOrderItem 传值data{" + data + "}");
        }
        OrderItemQuery orderItem = JsonUtil.json2Object(data, OrderItemQuery.class);

        int check = orderItemService.updateOrderItem(orderItem);
        if (check == 2) {
            new JsonResult(JsonResult.FAILURE, "非处理状态不可修改订单").toJson(response);
        } else if (check == 1) {
            new JsonResult(JsonResult.SUCCESS, "更新成功").toJson(response);
        } else if (check == 0) {
            new JsonResult(JsonResult.FAILURE, "更新失败").toJson(response);
        }
    }

    /**
     * 订单更新：物流更新
     *
     * @param orderIds     订单ID数组
     * @param intNo        物流编号基数
     * @param shippingComp 物流name
     * @param response     响应对象
     * @throws IOException       流异常
     * @throws GenerateException 生成编号异常
     */
    @RequestMapping("/updateShipping")
    public void updateOrderShippingById(int[] orderIds, String intNo, String shippingComp, String isCover, HttpServletResponse response) throws IOException, GenerateException {

        if (log.isInfoEnabled()) {
            log.info("OrderController updateOrderShippingById 传值ids[]{" + orderIds + "},intNo{" + intNo + "},shippingComp{" + shippingComp + "}");
        }
        int status = orderService.updateOrderShipping(orderIds, shippingComp, intNo, isCover);
        if (status == 0) {
            new JsonResult(JsonResult.FAILURE, "添加物流编号失败").toJson(response);
        } else if (status == 1) {
            new JsonResult(JsonResult.SUCCESS, "添加物流编号成功").toJson(response);
        } else if (status == 2) {

            new JsonResult(JsonResult.FAILURE, "有重复的物流编号").toJson(response);
        } else if (status == 3) {
            new JsonResult(JsonResult.FAILURE, "不存在此快递").toJson(response);
        } else if (status == 4) {
            new JsonResult(JsonResult.FAILURE, "物流编号必须是数字").toJson(response);
        }
    }

//    /**
//     * 更新订单状态
//     *
//     * @param order
//     * @param orderIds
//     * @param response
//     * @throws IOException
//     * @throws GenerateException
//     */
//    @RequestMapping("/updateStautsByOrder")
//    public void updateStatusByOrder(Order order, Integer[] orderIds, HttpServletResponse response) throws IOException, GenerateException {
//        if (log.isInfoEnabled()) {
//            log.info("OrderController updateStautsByOrder 传值orderIds[]{" + orderIds + "}order{" + order + "}");
//        }
//        int check = orderService.updateOrderStatusByOrder(order, orderIds);
//        if (check == 1) {
//            new JsonResult(JsonResult.SUCCESS, "更新成功").toJson(response);
//        } else {
//            new JsonResult(JsonResult.FAILURE, "更新失败").toJson(response);
//        }
//    }


    @RequestMapping("/addGift")
    public void addGiftOrderByHand(String queryProdVos, String[] orderNos, HttpServletResponse response) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("OrderController addGiftOrderByHand 传值queryProdVos[]{" + queryProdVos.toString() + "},orderNo{" + orderNos + "},orderItemType{");
        }

        if (!queryProdVos.startsWith("[")) {
            queryProdVos = "[" + queryProdVos + "]";
        }

        QueryProdVo[] queryProdVos1 = JsonUtil.jsonToArray(queryProdVos, QueryProdVo[].class);

        try {

            orderAnalyzeService.addGiftByHand(queryProdVos1, orderNos);

            new JsonResult(JsonResult.SUCCESS, "添加成功！").toJson(response);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("添加失败", e);
            }
            new JsonResult(JsonResult.FAILURE, "添加失败！" + e.getMessage()).toJson(response);
        }
    }

    @RequestMapping("/addOrder")
    public void addOrderByHand(String queryProdVos, AddOrderVo addOrderVo, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController addOrderByHand 传值queryProdVos[]{" + queryProdVos.toString() + "},addOrderVo{" + addOrderVo.toString() + "}");
        }

        if (!queryProdVos.startsWith("[")) {
            queryProdVos = "[" + queryProdVos + "]";
        }

        QueryProdVo[] queryProdVos1 = JsonUtil.jsonToArray(queryProdVos, QueryProdVo[].class);

        if (log.isInfoEnabled()) {
            log.info("OrderController addOrderByHand 传值queryProdVos1{" + queryProdVos1.toString() + "}," +
                    "addOrderVo{" + addOrderVo.toString() + "}");
        }
        try {

            List<String> stringList = orderAnalyzeService.addOrderByHand(addOrderVo, queryProdVos1);

            new JsonResult(JsonResult.SUCCESS, "添加订单成功！").addData(JsonResult.RESULT_TYPE_LIST, stringList).toJson(response);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("添加失败", e);
            }
            new JsonResult(JsonResult.FAILURE, "添加失败！" + e.getMessage()).toJson(response);
        }
    }

    /**
     * 复制订单

     @RequestMapping("/copyOrder")
     public void copyOrderByHand(Integer orderIds,HttpServletResponse response) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
     if (log.isInfoEnabled()) {
     log.info("OrderController copyOrderByHand 传过来的orderNo为" + orderIds );
     }
     try{

     AddOrderVo addOrderVo=orderAnalyzeService.copyOrderByHand(orderIds);
     if(log.isInfoEnabled()){
     log.info("addOrderVo的详情是："+addOrderVo.toString());
     }
     new JsonResult(JsonResult.SUCCESS,"复制订单成功！").addData("addOrderVo",addOrderVo).toJson(response);

     }catch (Exception e){
     if (log.isErrorEnabled()) {
     log.error("复制失败", e);
     }
     new JsonResult(JsonResult.FAILURE,"复制订单失败！"+e.getMessage()).toJson(response);
     }
     }
     */
    /**
     * 根据orderNo 删除一组订单记录
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/delete")
    public void deleteOrderById(HttpServletResponse response, Integer[] orderIds) throws IOException {
        orderService.deleteOrderById(orderIds);
        new JsonResult(JsonResult.SUCCESS, "删除成功").toJson(response);
    }

    /**
     * 根据orderNo 删除一组订单记录
     *
     * @param response
     * @param data
     * @throws IOException
     */
    @RequestMapping("/deleteItemList")

    public void deleteOrderItemById(HttpServletResponse response, String data) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("OrderController deleteOrderItemById 传值data{" + data.toString(

            ) + "}");
        }
        HashMap hashMap = (HashMap) JsonUtil.json2Object(data, Map.class);

        int check = orderItemService.deleteOrderItemById(Integer.parseInt(hashMap.get("id").toString()));
        if (check==1) {
            new JsonResult(JsonResult.SUCCESS, "删除成功").toJson(response);
        } else {
            new JsonResult(JsonResult.FAILURE, "删除失败").toJson(response);
        }
    }

    /**
     * 订单作废
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderCancellation")
    public void orderCancellation(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderCancellation(orderIds);
            new JsonResult(JsonResult.SUCCESS, "操作成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 订单恢复 作废-->待处理
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderRecover")
    public void orderRecover(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderRecover(orderIds);
            new JsonResult(JsonResult.SUCCESS, "恢复成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 导入进销存 即 订单 确认
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderConfirm")
    public void orderConfirm(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderConfirm(orderIds);
            new JsonResult(JsonResult.SUCCESS, "确认成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 店铺一览
     *
     * @param
     * @param response
     */
    @RequestMapping("/allShop")
    public void AllShop(HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询店铺：所有店铺一览");
        }
        // 查询店铺一览
        List<Shop> shopList = shopService.findAllShop();
        new JsonResult(JsonResult.SUCCESS, "查询所有店铺一览成功！")
                .addData(JsonResult.RESULT_TYPE_LIST, shopList)
                .toJson(response);
    }


    @RequestMapping("/extract2excel")
    public void extract2Excel(OrderQuery orderQuery, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Order&Item.xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            Workbook workbook = orderService.extractOrderAndItem2Excel(orderQuery);
            workbook.write(os);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }

    }
}
