package com.ejushang.spider.web.controller;

import com.ejushang.spider.query.BusinessLogQuery;
import com.ejushang.spider.service.log.IBusinessLogService;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: tin
 * Date: 14-1-16
 * Time: 下午2:58
 */
@Controller
@RequestMapping("/log")
public class LogController {
    private static final Logger log = LoggerFactory.getLogger(LogController.class);
    @Autowired
    private IBusinessLogService businessLogService;

    /**
     * 获取日志数据信息
     * @param businessLogQuery
     * @param response
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findBusinessLogByBusinessLog(BusinessLogQuery businessLogQuery, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("LogController中的方法findBusinessLogByBusinessLog参数列表BusinessLogQuery:{" + businessLogQuery.toString() + "}");
        }
        new JsonResult(JsonResult.SUCCESS, "查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, businessLogService.findBusinessLogByBusinessLog(businessLogQuery)).toJson(response);
    }

    /**
     * 获取日志详细信息
     * @param businessLogId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/itemList")
    public void findSqlLog(Integer businessLogId, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("LogController中的方法findSqlLog参数列表businessLogId:{" + businessLogId + "}");
        }
        new JsonResult(JsonResult.SUCCESS, "查询成功").addData(JsonResult.RESULT_TYPE_LIST, businessLogService.findSqlLogByBusinessLogId(businessLogId)).toJson(response);
    }
}
