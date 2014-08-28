package com.ejushang.spider.web.controller;

import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.exception.GenerateException;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.vo.DeliveryPrintVo;
import com.ejushang.spider.vo.OrderInspectionVo;
import com.ejushang.spider.vo.OrderPrintVo;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: Blomer
 * Date: 14-2-8
 * Time: 上午10:34
 * 发货管理Controller
 */

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;


    /**
     * 从 已验货||已打印||已确认 返回去 待处理
     *
     * @param response
     * @param orderIds
     * @throws java.io.IOException
     */
    @RequestMapping("/orderBackToWaitProcess")
    public void orderBackToWaitProcess(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderBackToWaitProcess(orderIds);
            new JsonResult(JsonResult.SUCCESS, "返回客服成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 根据orderIds 进行物流单的打印
     *
     * @param response
     * @param orderIds 订单id
     * @throws IOException
     */
    @RequestMapping("/deliveryPrint")
    public void deliveryPrint(HttpServletResponse response, Integer[] orderIds) throws IOException {
        if(log.isInfoEnabled()){
            log.info("OrderService中的deliveryPrint方法,参数orderIds = " + orderIds.toString());
        }
        if(orderIds == null){
            new JsonResult(JsonResult.SUCCESS, "没有要打印物流单的信息！").toJson(response);
            return ;
        }
        try {
            List<DeliveryPrintVo> deliveryPrintVoList = orderService.deliveryPrint(orderIds);
            new JsonResult(JsonResult.SUCCESS, "物流单打印结果").addData(JsonResult.RESULT_TYPE_LIST, deliveryPrintVoList).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 根据orderIds 进行订单打印
     *
     * @param response
     * @param orderIds 订单id
     * @throws IOException
     */
    @RequestMapping("/orderPrint")
    public void orderPrint(HttpServletResponse response, Integer[] orderIds) throws IOException {
        if(log.isInfoEnabled()){
            log.info("OrderService中的orderPrint方法,参数orderIds = " + orderIds.toString());
        }
        if(orderIds == null){
            new JsonResult(JsonResult.SUCCESS, "没有要打印发货单的信息！").toJson(response);
            return ;
        }
        try {
            List<OrderPrintVo> orderPrintVoList = orderService.orderPrint(orderIds);
            new JsonResult(JsonResult.SUCCESS, "发货单打印结果").addData(JsonResult.RESULT_TYPE_LIST, orderPrintVoList).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
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
     * @throws com.ejushang.spider.exception.GenerateException 生成编号异常
     */
    @RequestMapping("/updateShipping")
    public void updateOrderShippingById(int[] orderIds, String intNo, String shippingComp, String isCover, HttpServletResponse response) throws IOException, GenerateException {

        if (log.isInfoEnabled()) {
            log.info("OrderController updateOrderShippingById 传值ids[]{" + orderIds + "},intNo{" + intNo + "},shippingComp{" + shippingComp + "}");
        }
        Integer status = orderService.updateOrderShipping(orderIds, shippingComp, intNo, isCover);
        if (status.equals(0)) {
            new JsonResult(JsonResult.FAILURE, "添加物流编号失败").toJson(response);
        } else if (status.equals(1)) {
            new JsonResult(JsonResult.SUCCESS, "添加物流编号成功").toJson(response);
        } else if (status.equals(2)) {

            new JsonResult(JsonResult.FAILURE, "有重复的物流编号").toJson(response);
        } else if (status.equals(3)) {
            new JsonResult(JsonResult.FAILURE, "不存在此快递").toJson(response);
        }
    }


    /**
     * 确认打印
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderAffirmPrint")
    public void orderAffirmPrint(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderAffirmPrint(orderIds);
            new JsonResult(JsonResult.SUCCESS, "确认打印成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 返回已导入 即 返回已确认
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderBackToConfirm")
    public void orderBackToConfirm(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderBackToConfirm(orderIds);
            new JsonResult(JsonResult.SUCCESS, "返回已确认成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            throw new ErpBusinessException(e.getMessage());
        }
    }

    /**
     * 批量验货
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderBatchExamine")
    public void orderBatchExamine(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderBatchExamine(orderIds);
            new JsonResult(JsonResult.SUCCESS, "批量验证成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            throw new ErpBusinessException(e.getMessage());
        }
    }

    /**
     * 根据 shippingNos 物流编号 进行验货
     *
     * @param response
     * @param shippingNos
     * @throws IOException
     */
    @RequestMapping("/inspection")
    public void orderInspection(HttpServletResponse response, String[] shippingNos) throws IOException {
        try {
            List<OrderInspectionVo> orderInspectionVoList = orderService.orderInspection(shippingNos);
            new JsonResult(JsonResult.SUCCESS, "验证成功").addData(JsonResult.RESULT_TYPE_LIST, orderInspectionVoList).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }


    /**
     * 返回已打印
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderBackToPrint")
    public void orderBackToPrint(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderBackToPrint(orderIds);
            new JsonResult(JsonResult.SUCCESS, "返回已打印成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }

    /**
     * 确认发货
     *
     * @param response
     * @param orderIds
     * @throws IOException
     */
    @RequestMapping("/orderInvoice")
    public void orderInvoice(HttpServletResponse response, Integer[] orderIds) throws IOException {
        try {
            Integer count = orderService.orderInvoice(orderIds);
            new JsonResult(JsonResult.SUCCESS, "发货成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }
    }


}
