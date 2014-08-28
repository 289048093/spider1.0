package com.ejushang.spider.web.controller;

import com.ejushang.spider.bean.TransferInfoBean;
import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.domain.LogisticsInfo;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.service.delivery.ILogisticsInfoService;
import com.ejushang.spider.util.JsonUtil;
import com.ejushang.spider.vo.LogisticsInfoVo;
import com.ejushang.spider.vo.logistics.BackLogistics;
import com.ejushang.spider.web.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 物流信息处理
 *
 * create by Athens on 14-1-8
 */
@Controller
@RequestMapping(value = "/logistics")
public class LogisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger("logisticsLog");

    @Autowired
    private ILogisticsInfoService logisticsInfoService;

    /**
     * 接受第三方物流推送的数据.
     */
    @RequestMapping(value = "/back")
    public void logisticsBack(String param, HttpServletResponse response) throws IOException {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("第三方物流(kuaidi100)发送过来的物流信息: (" + param + ")");

        if (StringUtils.isBlank(param)) {
            response.getWriter().write(JsonUtil.objectToJson(new BackLogistics(false)));
            return;
        }
        try {
            logisticsInfoService.handleThirdLogisticsInfo(param.trim());
            response.getWriter().write(JsonUtil.objectToJson(new BackLogistics(true)));
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled())
                LOGGER.error("处理第三方物流查询平台回调数据时异常: ", e);

            response.getWriter().write(JsonUtil.objectToJson(new BackLogistics(false)));
        }
    }

    /**
     * 向第三方物流发送查询请求
     */
    @RequestMapping(value = "/send")
    public void sendLogisticsRequest(String orderNo, String company, String expressNo,
                                     String to, HttpServletResponse response) throws IOException {
        // 只是拆过多单的需要向 快递100 发送请求, 其他的是否应该由天猫提供物流信息?
        if (StringUtils.isBlank(orderNo)) {
            new JsonResult(JsonResult.FAILURE, "请务必传入订单号").toJson(response);
            return;
        }
        if (StringUtils.isBlank(company)) {
            new JsonResult(JsonResult.FAILURE, "请务必传入物流公司(英文字母)").toJson(response);
            return;
        }
        DeliveryType deliveryType;
        try {
            deliveryType = DeliveryType.valueOf(company);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled())
                LOGGER.error("未定义此物流公司(" + company + ")信息");

            new JsonResult(JsonResult.FAILURE, "未定义此物流公司(" + company + ")信息, 请联系技术部!").toJson(response);
            return;
        }
        if (deliveryType == null) {
            if (LOGGER.isWarnEnabled())
                LOGGER.warn("没有物流公司信息, 无法发送物流(" + expressNo + ")请求.");

            new JsonResult(JsonResult.FAILURE, "未定义此物流公司(" + company + ")信息, 请联系技术部!").toJson(response);
            return;
        }
        if (StringUtils.isBlank(expressNo)) {
            new JsonResult(JsonResult.FAILURE, "请务必传入物流编号").toJson(response);
            return;
        }
        if (StringUtils.isBlank(to)) {
            new JsonResult(JsonResult.FAILURE, "第三方物流查询平台要求必须传入收货地(中文), 到市即可. 如: 广东省深圳市").toJson(response);
            return;
        }

        try {
            logisticsInfoService.sendLogisticsInfoRequest(orderNo, deliveryType, expressNo, to);
        } catch (ErpBusinessException e) {
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
            return;
        } catch (DuplicateKeyException e) {
            // 只在并发的时候才可能出现
            new JsonResult(JsonResult.FAILURE, "已向第三方物流查询平台发送过请求.").toJson(response);
            return;
        }

        new JsonResult(JsonResult.SUCCESS).toJson(response);
    }

    /**
     * 批量查询物流信息
     * @param expressNos
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findByExpressNo")
    public void findByExpressNo(String expressNos,HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        if(StringUtils.isBlank(expressNos)){
            new JsonResult(JsonResult.FAILURE,"物流单号不能为空，请输入物流单号").toJson(response);
            return;
        }
        String[] expressArr = expressNos.split("\\n");
        if(expressArr == null){
            throw new ErpBusinessException("参数解析错误！expressNos = " + expressNos);
        }
        List<LogisticsInfoVo> logisticsInfoList = logisticsInfoService.findLogisticsInfoByExpressNos(Arrays.asList(expressArr));
        new JsonResult(JsonResult.SUCCESS,"物流信息查询成功！")
                .addData(JsonResult.RESULT_TYPE_LIST,logisticsInfoList)
                .toJson(response);
        return;
    }

    /**
     * 查询中转信息
     * @param expressNo
     * @param response
     * @throws IOException
     */
    @RequestMapping("/detailByExpressNo")
    public void detailByExpressNo(String expressNo,HttpServletResponse response) throws IOException {
        List<TransferInfoBean> transferInfoBeanList = logisticsInfoService.findTransferInfoByExpressNo(expressNo);
        new JsonResult(JsonResult.SUCCESS,"中转信息查询成功！")
                .addData(JsonResult.RESULT_TYPE_LIST,transferInfoBeanList)
                .toJson(response);
        return;
    }

    /**
     * 导出
     * @param expressNos
     * @param response
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/exportByExpressNos")
    public void exportByExpressNos(String expressNos,HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        if(StringUtils.isBlank(expressNos)){
            new JsonResult(JsonResult.FAILURE,"物流单号不能为空，请输入物流单号").toJson(response);
            return;
        }

        String[] expressArr = expressNos.split("\\n");
        if(expressArr == null){
            throw new ErpBusinessException("参数解析错误！expressNos = " + expressNos);
        }

        File file = logisticsInfoService.exportLogisticsFullInfoBeanByExpressNos(Arrays.asList(expressArr));

        InputStream is = new BufferedInputStream(new FileInputStream(file));
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        //清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        FileCopyUtils.copy(is,toClient);
//        byte[] buffer = new byte[is.available()];
//        try{
//            is.read(buffer);
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
//            response.addHeader("Content-Length", "" + file.length());
//            response.setContentType("application/octet-stream");
//            toClient.write(buffer);
//            toClient.flush();
//        }
//        finally {
//            is.close();
//            toClient.close();
//        }


    }

}
