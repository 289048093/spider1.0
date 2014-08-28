package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.query.OrderFetchQuery;
import com.ejushang.spider.service.order.IOrderFetchService;
import com.ejushang.spider.service.product.IBrandService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 物流设计Controller
 */
@RequestMapping("/orderfetch")
@Controller
public class OrderFetchController {
    private static final Logger log = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    IOrderFetchService orderFetchService;
    /**
     * 获得所有订单抓取信息
     */
    @RequestMapping("/list")
    public void findBrandAll(OrderFetchQuery orderFetchQuery,HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("订单抓取查询的条件实体" + orderFetchQuery);
        }
        Page page = orderFetchService.findOrderFetchs(orderFetchQuery);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }
}
