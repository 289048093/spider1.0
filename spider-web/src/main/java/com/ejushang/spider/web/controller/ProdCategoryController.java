package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.ProdCategory;
import com.ejushang.spider.query.ProdCategoryQuery;
import com.ejushang.spider.service.product.IProdCategoryService;
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
 * 产品分类设计Controller
 */
@RequestMapping("/prodCategory")
@Controller
public class ProdCategoryController {
    private static final Logger log = LoggerFactory.getLogger(ProdCategoryController.class);

    @Autowired
    IProdCategoryService prodCategoryService;

    /**
     * 保存产品分类信息
     * w
     *
     * @param prodCategory 产品分类信息实体
     * @return 插入的条数
     */
    @RequestMapping("/add")
    public void saveProdCategory(ProdCategory prodCategory, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("产品分类：prodCategory" + prodCategory);
        }
        int count = prodCategoryService.saveProdCategory(prodCategory);
        if (count != -1) {
            new JsonResult(JsonResult.SUCCESS).setMsg("保存成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
            return;
        }
        new JsonResult(JsonResult.FAILURE).setMsg("产品分类名已经存在!").toJson(response);
    }

    /**
     * 通过ID获得产品分类信息
     *
     * @param id 产品分类ID
     * @return 产品分类信息实体
     */
    @RequestMapping("/listId")
    public void findProdCategoryById(Integer id, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("产品分类查询id：" + id);
        }
        ProdCategory prodCategory = prodCategoryService.findProdCategoryById(id);
        log.info("通过ID获得的结果" + prodCategory);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, prodCategory).toJson(response);
    }

    /**
     * 获得所有产品分类技术信息
     */
    @RequestMapping("/list")

    public void findProdCategoryAll(ProdCategoryQuery prodCategoryQuery, HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("产品分类查询实体 prodCategoryQuery：" + prodCategoryQuery);
        }
        Page page = prodCategoryService.findProdCategoryAll(prodCategoryQuery);
        log.info("产品分类查询返回的结果 :page.getResult()" + page.getResult());
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }

    /**
     * 获得所有产品分类技术信息
     */
    @RequestMapping("/forList")

    public void findProdCategoryForList(HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ProdCategory> list = prodCategoryService.findProdCategoryForList(new ProdCategoryQuery());
        if (log.isInfoEnabled()) {
            log.info("产品分类查询结果 list：" + list);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST, list).toJson(response);
    }

    /**
     * 修改产品分类信息
     *
     * @param prodCategory 产品分类信息实体
     * @return 修改的条数
     */
    @RequestMapping("/update")

    public void updateProdCategory(ProdCategory prodCategory, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("产品分类修改实体 prodCategory：" + prodCategory);
        }
        int count = prodCategoryService.updateProdCategory(prodCategory);
        log.info("修改的条数" + count);
        if (count != -1) {
            new JsonResult(JsonResult.SUCCESS).setMsg("修改成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
            return;
        }
        new JsonResult(JsonResult.FAILURE).setMsg("产品分类名已经存在!").toJson(response);
    }

    /**
     * 删除产品分类信息 通过ID
     *
     * @param idArray 产品分类id数组
     * @return 删除的条数
     */
    @RequestMapping("/delete")
    public void deleteProdCategory(int[] idArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("产品分类删除的id数组 idArray：" + idArray);
        }
        int count = prodCategoryService.deleteProdCategory(idArray);
        log.info("删除的条数" + count);
        if (count == -1) {
            new JsonResult(JsonResult.FAILURE).setMsg("分类下存在商品，不能删除！").toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("删除成功！").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }
}
