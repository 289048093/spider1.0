package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Conf;
import com.ejushang.spider.domain.Order;
import com.ejushang.spider.domain.OrderItem;
import com.ejushang.spider.erp.service.order.OrderItemService;
import com.ejushang.spider.erp.util.SystemConfConstant;
import com.ejushang.spider.service.delivery.ILogisticsInfoService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.service.sysconfig.IConfService;
import com.ejushang.spider.util.DateUtils;
import com.ejushang.spider.vo.OrderItemVo;
import com.ejushang.spider.vo.OrderVo;
import com.ejushang.spider.web.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 预警
 * create by Athens on 14-1-9
 */
@Controller
@RequestMapping("/warn")
public class EarlyWarningController {

    private static final Logger LOG = LoggerFactory.getLogger(EarlyWarningController.class);

    @Autowired
    private IConfService confService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ILogisticsInfoService logisticsInfoService;

    @Autowired
    private OrderItemService orderItemService;

    /** 报警时间 >> 最近的 16 点以前买家已付款, 还未 确认发货的订单号 及 总数 */
    @RequestMapping("/nosend")
    public void noSendOrder(HttpServletResponse response) throws IOException {
        String triggerWarnTime = queryConfByKey(SystemConfConstant.NO_SEND_GOODS_TRIGGER_WARN_TIME, "16:00");

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String compareTime = DateUtils.formatDate(now, DateUtils.DateFormatType.SIMPLE_DATE_FORMAT_STR);

        if (now.before(DateUtils.parseDate(compareTime + " 17:30", DateUtils.DateFormatType.DATE_MINUTE_FORMAT_STR))) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            compareTime = DateUtils.formatDate(calendar.getTime(), DateUtils.DateFormatType.SIMPLE_DATE_FORMAT_STR);
        }

        StringBuilder hourMinute = new StringBuilder();
        hourMinute.append(compareTime).append(" ").append(triggerWarnTime);

        List<Map<String, Object>> noSendOrderList = orderService.findOrderNoLaterPay(hourMinute.toString());
        if (noSendOrderList == null || noSendOrderList.size() == 0) {
            emptyReturn(response);
            return;
        }

        returnMsg(response, "有 " + noSendOrderList.size() + " 条订单在 "
                + hourMinute.toString() + " 以前付了款却还未发货, 请尽快处理!", noSendOrderList);
    }

    private void emptyReturn(HttpServletResponse response) throws IOException {
        returnMsg(response, StringUtils.EMPTY, Collections.EMPTY_LIST);
    }

    private void returnMsg(HttpServletResponse response, String msg, List list) throws IOException {
        new JsonResult(JsonResult.SUCCESS, msg).addData(JsonResult.RESULT_TYPE_LIST, list).toJson(response);
    }

    // ========== 上面的 url 权限属于 供应链运维专员/主管/经理 ==========
    // =========== 下面的 url 权限属于 物流跟单专员/主管/经理 ===========

    /** 报警时间 >> 已经在今天以前确认发了货, 到现在却还没有物流扫描信息的订单号 及 总数 */
    @RequestMapping("/nologistics")
    public void noLogisticsInfo(HttpServletResponse response) throws IOException {
        Calendar calendar = Calendar.getInstance();
        String today = DateUtils.formatDate(calendar.getTime(), DateUtils.DateFormatType.SIMPLE_DATE_FORMAT_STR);

        // 查询已经发了货, 却还没有物流扫描信息的 订单号
        List<Map<String, Object>> noLogisticsInfoList = orderService.findNoLogisticsInfoOrderNoLaterSend(today);
        if (noLogisticsInfoList == null || noLogisticsInfoList.size() == 0) {
            emptyReturn(response);
            return;
        }

        returnMsg(response, "有 " + noLogisticsInfoList.size() +
                " 条订单已在 " + today + " 以前发了货却还没有物流信息(数据基于第三方物流平台, 可能会有延迟), 请及时跟踪!", noLogisticsInfoList);
    }

    /** 报警时间 >> 实时, 包裹的最新物流时间距离当前超过 24 小时 */
    @RequestMapping("/staytimeout")
    public void bundleStayTimeout(HttpServletResponse response) throws IOException {
        int hourCount = queryConfByKey(SystemConfConstant.LATEST_TIME_DISTANCE_HOUR, 24);

        List<Map<String, Object>> logisticsInfoList = logisticsInfoService.findNotSuccessLogisticsByLaTestTime(hourCount);
        if (logisticsInfoList == null || logisticsInfoList.size() == 0) {
            emptyReturn(response);
            return;
        }

        returnMsg(response, "有 " + logisticsInfoList.size() + " 条订单的物流已经超过 "
                + hourCount + " 小时没动静了(数据基于第三方物流平台, 可能会有延迟), 请及时跟踪!", logisticsInfoList);
    }

    private String queryConfByKey(String key, String defaultValue) {
        String confValue = queryConfByKey(key);
        return StringUtils.isBlank(confValue) ? defaultValue : confValue;
    }

    private int queryConfByKey(String key, int defaultValue) {
        return NumberUtils.toInt(queryConfByKey(key), defaultValue);
    }

    private String queryConfByKey(String key) {
        Conf conf = confService.findConfByKey(key);
        return (conf == null) ? StringUtils.EMPTY : conf.getConfigValue().trim();
    }

    private List<String> queryConfSplit(String key, String defaultValue) {
        String address = queryConfByKey(key);
        if (StringUtils.isBlank(address))
            address = defaultValue;

        return Arrays.asList(address.split("/"));
    }

    private boolean contains(String sendTo, List<String> addressList) {
        if (addressList != null && addressList.size() > 0) {
            for (String address : addressList) {
                if (sendTo.contains(address))
                    return true;
            }
        }
        return false;
    }

    /**
     * 报警时间实时 >>
     *
     * 广东省内, 有第一条物流信息后, 48小时(两天)未签收;
     * 江苏、浙江、上海, 72小时(3天)未签收;
     * 广西、福建、海南、江西、安徽、湖北、湖南、河南、北京、天津、河北、山东、山西、重庆, 96小时(4天)未签收;
     * 内蒙古、辽宁、吉林、黑龙江、云南、贵州、四川、陕西、甘肃、青海、宁夏、西藏, 144小时(6天)未签收.
     */
    @RequestMapping("/nosign")
    public void noSign(HttpServletResponse response) throws IOException {
        // 超过 48 小时包含的物流数据里, 会包含后面三类数据
        int gdHourCount = queryConfByKey(SystemConfConstant.GD_DISTANCE_HOUR, 48);
        List<Map<String, Object>> logisticsInfoList = logisticsInfoService.findNotSuccessLogisticsByFirstTime(gdHourCount);
        if (logisticsInfoList == null || logisticsInfoList.size() == 0) {
            emptyReturn(response);
            return;
        }

        List<String> gdList = queryConfSplit(SystemConfConstant.GD_ADDRESS, "广东");

        int jzhHourCount = queryConfByKey(SystemConfConstant.JZH_DISTANCE_HOUR, 72);
        List<String> jzhList = queryConfSplit(SystemConfConstant.JZH_ADDRESS, "江苏/浙江/上海");

        int slowHourCount = queryConfByKey(SystemConfConstant.SLOW_DISTANCE_HOUR, 96);
        List<String> slowList = queryConfSplit(SystemConfConstant.SLOW_ADDRESS, "广西/福建/海南/江西/安徽/湖北/湖南/河南/北京/天津/河北/山东/山西/重庆");

        int pyHourCount = queryConfByKey(SystemConfConstant.PY_DISTANCE_HOUR, 144);
        List<String> pyList = queryConfSplit(SystemConfConstant.PY_ADDRESS, "内蒙古/辽宁/吉林/黑龙江/云南/贵州/四川/陕西/甘肃/青海/宁夏/西藏");

        List<Map<String, Object>> backList = new ArrayList<Map<String, Object>>();
        String sendTo;
        Date firstTime;
        Calendar calendar = null;
        for (Map<String, Object> objectMap : logisticsInfoList) {
            // 物流收货地址
            sendTo = String.valueOf(objectMap.get("sendTo"));
            if (contains(sendTo, gdList)) {
                backList.add(objectMap);
                continue;
            }

            firstTime = DateUtils.parseDate(String.valueOf(objectMap.get("firstTime")), DateUtils.DateFormatType.DATE_FORMAT_STR);
            if (contains(sendTo, jzhList)) {
                // 江浙沪
                calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, -jzhHourCount);
                if (firstTime.before(calendar.getTime()))
                    backList.add(objectMap);
            } else if (contains(sendTo, slowList)) {
                // 京津...
                calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, -slowHourCount);
                if (firstTime.before(calendar.getTime()))
                    backList.add(objectMap);
            } else if (contains(sendTo, pyList)) {
                // 云贵甘陕...
                calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, -pyHourCount);
                if (firstTime.before(calendar.getTime()))
                    backList.add(objectMap);
            }
            // help gc
            if (calendar != null)
                calendar = null;
        }

        if (backList.size() == 0) {
            emptyReturn(response);
            return;
        }

        if (LOG.isInfoEnabled())
            LOG.info("超过设置时限还未签收的订单:" + backList);

        returnMsg(response, "有 " + backList.size() + " 条订单已经超过设置时限还未签收(数据基于第三方物流平台, 可能会有延迟), 请及时跟踪!",  backList);
    }

    /**
     * 单个订单详情
     * @param response
     * @param orderNo    订单ID
     */
    @RequestMapping("/singleorderdetail")
    public void singleOrderDetail(HttpServletResponse response,String orderNo) throws IOException {
        if (orderNo == null) {
            new JsonResult(JsonResult.FAILURE, "订单编号不能为空").toJson(response);
            return;
        }
        OrderVo datas;
        try {
            datas = orderService.findSingleDetailOrder(orderNo);
        } catch (Exception e) {
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
            return;
        }
        if (datas == null) {
            new JsonResult(JsonResult.FAILURE, "该订单编号没有对应的订单").toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).addData("order", datas).toJson(response);
    }

    /**
     * 查询订单的商品清单
     * @param response
     * @param orderNo        订单ID
     * @throws IOException
     */
    @RequestMapping("/listorderitems")
    public void listOrderItems(HttpServletResponse response,String orderNo) throws IOException {
        if(orderNo==null){
            new JsonResult(JsonResult.FAILURE,"订单编号不能为空").toJson(response);
            return;
        }
        List<OrderItemVo> orderItems =  orderItemService.findOrderItemsByOrderNo(orderNo);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT,orderItems).toJson(response);
    }
}
