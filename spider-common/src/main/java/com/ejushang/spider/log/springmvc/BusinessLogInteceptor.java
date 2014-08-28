package com.ejushang.spider.log.springmvc;


import com.ejushang.spider.domain.BusinessLog;
import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.domain.Resource;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.log.BusinessLogHolder;
import com.ejushang.spider.service.log.IBusinessLogService;
import com.ejushang.spider.service.permission.IResourceService;
import com.ejushang.spider.util.JsonUtil;
import com.ejushang.spider.util.PermissionUtils;
import com.ejushang.spider.util.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC拦截器
 * 首先在preHandle方法中记录请求的url以及登录用户等信息,将结果放入TheadLocal
 * 在afterCompletion方法中取出ThreadLocal中的业务日志对象保存到数据库,并清理ThreadLocal
 *
 * User: liubin
 * Date: 14-1-13
 */
public class BusinessLogInteceptor implements HandlerInterceptor {

    @Autowired
    IBusinessLogService businessLogService;
    @Autowired
    IResourceService resourceService;

    private static final Logger log = LoggerFactory.getLogger(BusinessLogInteceptor.class);

    private BusinessLogHolder businessLogHolder = BusinessLogHolder.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler)
        throws Exception {

        BusinessLog businessLog = new BusinessLog();
        try {
            businessLogHolder.addBusinessLog(businessLog);
            //记录请求参数
            businessLog.setParams(JsonUtil.objectToJson(request.getParameterMap()));

            //记录用户信息
            User user = SessionUtils.getUser();
            if(user != null) {
                businessLog.setUserId(user.getId());
                businessLog.setUserName(user.getUsername());
            }
            businessLog.setIp(request.getRemoteAddr());
            businessLog.setRequestUrl(request.getRequestURL().toString());

            //记录请求模块信息
            String operationUrl = PermissionUtils.trimUrlSuffix(WebUtils.getPathWithinApplication(request));
            if(!StringUtils.isBlank(operationUrl)) {
                Operation operation = resourceService.getOperationByUrl(operationUrl);
                if(operation != null) {
                    businessLog.setOperationId(operation.getId());
                    businessLog.setOperationName(operation.getName());
                    Resource resource = resourceService.get(operation.getResourceId());
                    if(resource != null) {
                        businessLog.setResourceName(resource.getName());
                    }
                }
            }

            long startTime = System.currentTimeMillis();
            //暂时把开始时间放入这个字段,等执行结束拿出来计算真正的执行时间
            businessLog.setExecutionTime(startTime);
        } catch (Exception e) {
            log.error("记录业务操作日志的时候出错,", e);
        }

        return true;
    }

    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, 
        Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            BusinessLog businessLog = businessLogHolder.pollBusinessLog();
            if(businessLog == null) {
                log.error("afterCompletion中发现BusinessLog为空,程序有bug?");
                return;
            }
            if(businessLog.getExecutionTime() != null) {
                businessLog.setExecutionTime(System.currentTimeMillis() - businessLog.getExecutionTime());
            }
            if(!businessLog.getSqlLogs().isEmpty()) {
                //保存BusinessLog以及SqlLog对象,sqlLog不为空的情况才保存
                businessLogService.createBusinessLog(businessLog);
            }
        } catch (Exception e) {
            log.error("保存业务日志的时候出错", e);
        }

    }
}