package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Product;
import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.erp.service.repository.RepositoryService;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.query.StorageQuery;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.util.Page;
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
 * Date: 13-12-31
 * Time: 上午11:47
 */
@Controller
@RequestMapping("/storage")
public class StorageController {
    private static final Logger log = LoggerFactory.getLogger(RepositoryService.class);

    @Autowired
    private IStorageService storageService;

    /**
     * 通过StorageQuery查询库存
     *
     * @param query    StorageQuery对象引用
     * @param response 对象响应
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findByStorage(StorageQuery query, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("StorageController findByStorage query{" + query + "}");
        }
        Page page = new Page();
        page = storageService.findJoinStorage(query);
        new JsonResult(JsonResult.SUCCESS, "查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }



    /**
     * 通过id查询库存
     *
     * @param id       库存id
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping("/findById")
    public void findStorageById(String id, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("StorageController findStorageById 传值id{" + id + "}");
        }
        Integer myid = Integer.parseInt(id.trim());
        JsonResult json = new JsonResult(JsonResult.SUCCESS, "查询成功");
        json.addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, storageService.findStorageById(myid));
        json.toJson(response);
    }

    /**
     * 通过id删除库存
     *
     * @param id       库存id
     * @param response 响应对象
     * @throws IOException
     */
    @RequestMapping("/delete")
    public void deleteStorageById(String id, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("StorageController deleteStorageById 传值id{" + id + "}");
        }
        Integer myid = Integer.parseInt(id.trim());
        storageService.deleteStorageById(myid);
        JsonResult json = new JsonResult(JsonResult.SUCCESS, "删除成功");
        json.toJson(response);


    }

    /**
     * 通过Storage对象更新库存
     *
     * @param response 响应对象
     * @param storage  库存对象
     * @throws IOException
     */
    @RequestMapping("/update")
    public void updateStorageByStorage(HttpServletResponse response, Storage storage) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("StorageController updateStorageByStorage 传值storage{" + storage + "}");
        }
        JsonResult json = null;
        Integer che = storageService.updateStorage(storage);
        if (che != 0) {
            json = new JsonResult(JsonResult.SUCCESS, "更新成功");
        } else {
            json = new JsonResult(JsonResult.FAILURE, "更新失败");
        }
        json.toJson(response);
    }


}
