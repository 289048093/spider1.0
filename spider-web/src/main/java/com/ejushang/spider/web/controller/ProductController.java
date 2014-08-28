package com.ejushang.spider.web.controller;

import com.ejushang.spider.constant.OrderItemType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.QueryProdVo;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 商品设计Controller
 */
@RequestMapping("/product")
@Controller
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    IProductService productService;


    /**
     * 保存商品信息
     * w
     *
     * @param product 商品信息实体
     * @return 插入的条数
     */
    @RequestMapping("/add")
    public void saveProduct(Product product, Storage storage, String type, HttpServletResponse response) throws IOException {

        if (log.isInfoEnabled()) {
            log.info("商品保存实体 product：" + product);
        }
        int count = productService.saveProduct(product, storage);
        if (count == -1) {
            new JsonResult(JsonResult.FAILURE).setMsg("此条形码已经存在!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("保存成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);

    }

    /**
     * 通过ID获得商品信息
     *
     * @param id 商品ID
     * @return 商品信息实体
     */
    @RequestMapping("/findProductById")
    public void findProductById(Integer id, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("商品查询id：" + id);
        }
        Product product = productService.findProductById(id);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, product).toJson(response);
    }

    /**
     * 获得所有商品技术信息
     */
    @RequestMapping("/list")

    public void findProductAll(ProductQuery productQuery, HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("商品查询实体 productQuery：" + productQuery);
        }
        if ("prodCode".equals(productQuery.getSearchType())) {
            productQuery.setProdCode(productQuery.getSearchValue());
        }
        if ("prodName".equals(productQuery.getSearchType())) {
            productQuery.setProdName(productQuery.getSearchValue());
        }
        if ("prodNo".equals(productQuery.getSearchType())) {
            productQuery.setProdNo(productQuery.getSearchValue());
        }
        Page page = productService.findProductAll(productQuery);
        if (log.isInfoEnabled()) {
            log.info("商品查询的分页结果：result" + page.getResult());
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }

    /**
     * 修改商品信息
     *
     * @param product 商品信息实体
     * @return 修改的条数
     */
    @RequestMapping("/update")

    public void updateProduct(Product product, Storage storage, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("商品修改实体 product：" + product);
        }
        int count = productService.updateProduct(product, storage);
        if (log.isInfoEnabled()) {
            log.info("被修改的数量", count);
        }
        if (count == -1) {
            if (log.isInfoEnabled()) {
                log.info("类型是什么 product.getType()" + product.getType());
            }
            new JsonResult(JsonResult.FAILURE).setMsg("此条形码已经存在!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("修改成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 删除商品信息 通过ID
     *
     * @param idArray 商品id
     * @return 删除的条数
     */
    @RequestMapping("/delete")
    public void deleteProduct(int[] idArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("商品删除id数组 idArray：" + idArray);
        }
        int count = 0;
        try {
            count = productService.deleteProduct(idArray);
        } catch (Exception e) {
            log.error("删除失败", e);
            new JsonResult(JsonResult.FAILURE).setMsg(e.getMessage()).toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("删除成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);

    }

    @RequestMapping("/findProduct")
    public void findProduct(String param, String paramType, OrderItemType orderItemType, Integer repoId, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("输入的商品信息为：" + param + paramType + orderItemType);
        }
        List<QueryProdVo> queryProdVoList = productService.findProductByProd(param, paramType, orderItemType, repoId);
        if (log.isInfoEnabled()) {
            log.info("商品查询的结果：" + queryProdVoList);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST, queryProdVoList).toJson(response);
    }


    /**
     * 导单其实也是添加商品
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/leadingIn")
    public void uploadTemplet(MultipartFile multipartFile,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("验证模版中");
        }
        String fileType = "";
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
                    fileName.lastIndexOf(".") + 4);
        } catch (Exception e) {
            log.error("解析excel出错", e);
            fileType = "";
        }
        if (!fileType.toLowerCase().equals("xls")) {
            new JsonResult(JsonResult.FAILURE).setMsg("导入的文件格式不正确，不是excel文件")
                    .toJson(response);
            if (log.isInfoEnabled()) {
                this.log.info("导入的文件格式不正确，应该不是excel文件");
            }
            return;
        }
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(multipartFile.getInputStream());
            HSSFSheet sheet = wb.getSheetAt(0);
            log.info(" sheet.getLastRowNum()" + sheet.getLastRowNum());
            try {
                productService.leadInProduct(sheet);
            } catch (Exception e) {
                new JsonResult(JsonResult.FAILURE).setMsg(e.getMessage())
                        .toJson(response);
                log.error("验证失败", e);
                return;
            }
            new JsonResult(JsonResult.SUCCESS).setMsg("导入成功!").addData(JsonResult.STATUS, "success")
                    .toJson(response);
        } catch (Exception e) {
            log.error("导单出现异常了", e);
        }
    }


}
