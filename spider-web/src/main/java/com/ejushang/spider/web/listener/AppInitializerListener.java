package com.ejushang.spider.web.listener;

import com.ejushang.spider.SpiderApplication;
import com.ejushang.spider.util.AppConfigUtil;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: Baron.Zhang
 * Date: 14-1-2
 * Time: 下午4:57
 */
public class AppInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpiderApplication spiderApplication = SpiderApplication.getInstance();
        spiderApplication.init(WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()), servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
