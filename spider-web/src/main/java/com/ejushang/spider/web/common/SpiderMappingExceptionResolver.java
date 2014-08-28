package com.ejushang.spider.web.common;

import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.exception.ErpException;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: liubin
 * Date: 14-2-7
 *
 * springmvc异常信息记录类
 *
 */
public class SpiderMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 记录错误日志
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);

        response.setStatus(997); //系统错误的错误吗

        String msg = "系统发生错误,请联系管理员";
        if(ex instanceof ErpBusinessException) {
            msg = ex.getMessage();
        } else if(ex instanceof ErpException) {
            msg = ex.getMessage();
        }

        ModelAndView mav = new ModelAndView();
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("success", false);
        attributes.put("msg", msg);
        view.setAttributesMap(attributes);
        mav.setView(view);
        return mav;
    }


}