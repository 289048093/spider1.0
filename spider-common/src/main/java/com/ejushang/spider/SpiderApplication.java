package com.ejushang.spider;

import com.ejushang.spider.service.permission.IResourceService;
import com.ejushang.spider.util.AppConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import javax.servlet.ServletContext;

/**
 * 系统初始化类,在系统启动的时候调用init方法,完成系统内部的初始化工作
 * User: liubin
 * Date: 14-1-14
 */
public class SpiderApplication {

    private static final SpiderApplication instance = new SpiderApplication();
    private SpiderApplication() {}
    public static final SpiderApplication getInstance() {
        return instance;
    }

    private ApplicationContext applicationContext;

    private ServletContext servletContext;

    private static final Logger log = LoggerFactory.getLogger(SpiderApplication.class);

    private boolean initialized = false;

    private IResourceService resourceService;


    /**
     * 启动的时候需要调用初始化方法
     */
    public synchronized void init(ApplicationContext applicationContext, ServletContext servletContext) {
        if(initialized) return;
        try {
            Assert.notNull(applicationContext, "applicationContext will not be null!");

            this.applicationContext = applicationContext;
            this.servletContext = servletContext;

            initBeans();


            if(servletContext != null) {
                servletContext.setAttribute("ctx", AppConfigUtil.getInstance().get("ctx"));
            }

            initialized = true;
        } catch (Exception e) {
            log.error("系统初始化的时候出错", e);
        }
    }

    private void initBeans() {
        resourceService = applicationContext.getBean(IResourceService.class);
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


}
