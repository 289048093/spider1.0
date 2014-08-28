package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.service.product.IBrandService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.util.SessionUtils;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * User: liubin
 * Date: 14-2-9
 *
 */
@RequestMapping("/static")
@Controller
public class StaticResourceController {
    private static final Logger log = LoggerFactory.getLogger(StaticResourceController.class);

    @RequestMapping("/ctx_info")
    public String getCtxInfo(Model model) {
        model.addAttribute("user", SessionUtils.getUser());
        return "/static/ctx_info";
    }


}
