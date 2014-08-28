package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.query.BrandQuery;
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
@RequestMapping("/brand")
@Controller
public class BrandController {
    private static final Logger log = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    IBrandService brandService;

    /**
     * 保存物流信息
     *
     * @param brand 物流信息实体
     * @return 插入的条数
     */
    @RequestMapping("/add")
    public void saveBrand(Brand brand, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("brand实体" + brand);
        }
        int count = brandService.saveBrand(brand);
        if (count != -1) {
            new JsonResult(JsonResult.SUCCESS).setMsg("保存成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
            return;
        }
        new JsonResult(JsonResult.FAILURE).setMsg("品牌名已经存在!").toJson(response);
    }


    /**
     * 获得所有物流技术信息
     */
    @RequestMapping("/list")
    public void findBrandAll(BrandQuery brandQuery, HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询的条件实体" + brandQuery);
        }
        Page page = brandService.findBrandAll(brandQuery);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }

    /**
     * 获得所有物流技术信息
     */
    @RequestMapping("/forList")
    public void findBrandForList(HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Brand> list = brandService.findBrandForList(new BrandQuery());
        if (log.isInfoEnabled()) {
            log.info("查询的条件结果" + list);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST, list).toJson(response);
    }

    /**
     * 修改物流信息
     *
     * @param brand 物流信息实体
     * @return 修改的条数
     */
    @RequestMapping("/update")

    public void updateBrand(Brand brand, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("修改的条件实体" + brand);
        }
        int count = brandService.updateBrand(brand);
        if (count == -1) {
            new JsonResult(JsonResult.FAILURE).setMsg("品牌名已经存在!").toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("修改成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 删除物流信息 通过ID
     *
     * @param idArray 物流id字符串
     * @return 删除的条数
     */
    @RequestMapping("/delete")
    public void deleteBrand(int[] idArray, HttpServletResponse response) throws IOException {
        int count = brandService.deleteBrand(idArray);
        if (count == -1) {
            new JsonResult(JsonResult.FAILURE).setMsg("此品牌已经被使用，不能删除！").toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("删除成功！").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }
}
