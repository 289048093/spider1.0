package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Repository;
import com.ejushang.spider.query.RepositoryQuery;
import com.ejushang.spider.service.repository.IRepositoryService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: tin
 * Date: 13-12-26
 * Time: 下午4:10
 */
@Controller
@RequestMapping("/repository")
public class RepositoryController {
    private static final Logger log = LoggerFactory.getLogger(RepositoryController.class);

    @Autowired
    private IRepositoryService repositoryService;


    /**
     * 通过ID批量删除仓库
     *
     * @param res response响应
     * @param id  id参数
     * @throws IOException
     */
    @RequestMapping("/delete")
    public void deleteRepository(HttpServletResponse res, Integer[] id) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("controller deleteRepository 传值数组id{" + id + "}");
        }
        JsonResult json = null;


      int  check=repositoryService.deleteRepositoryById(id);
          if(check==1){
              json = new JsonResult(JsonResult.SUCCESS, "删除成功");
          }
        else if(check==2){
              json = new JsonResult(JsonResult.FAILURE, "仓库里库存不为空，不能执行删除操作");
          }
        json.toJson(res);
    }

    /**
     * 通过repository对象查询仓库信息
     *
     * @param res        response响应
     * @param repository Repository对象
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findRepositoryByRepository(HttpServletResponse res, RepositoryQuery repository) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("controller findRepositoryByRepository 传值对象 Repository" + repository.toString());
        }
        Page page = new Page();
        page = repositoryService.findRepositoryByRepository(repository);
        new JsonResult(JsonResult.SUCCESS, "查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(res);
    }

    @RequestMapping("/findAll")
    public void findRepository(HttpServletResponse res, RepositoryQuery repository) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("controller findAll 传值对象 Repository" + repository.toString());
        }


        new JsonResult(JsonResult.SUCCESS, "查询成功").addData(JsonResult.RESULT_TYPE_LIST, repositoryService.findRepositoryAll()).toJson(res);
    }

    /**
     * 通过Repository对象更新仓库
     *
     * @param res        response响应
     * @param repository repository实体变量
     * @throws IOException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateRepositoryByRepsoitory(HttpServletResponse res, Repository repository) throws IOException {

        if (log.isInfoEnabled()) {
            log.info("controller updateRepositoryByRepsoitory 传值对象Repository{" + repository.toString() + "]");
        }
        JsonResult json = null;
        Integer check = repositoryService.updateRepository(repository);
        if (check == 0) {
            json = new JsonResult(JsonResult.FAILURE, "未找到存在的Id");
            json.toJson(res);
        } else {
            json = new JsonResult(JsonResult.SUCCESS, "更新成功");
            json.toJson(res);
        }
    }

    /**
     * 通过Repository参数添加仓库
     *
     * @param res        response响应
     * @param repository Repository参数
     * @throws IOException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void saveRepository(HttpServletResponse res, Repository repository) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("controller saveRepository 传值对象Repository{" + repository.toString() + "}");
        }
        JsonResult json = null;
        Integer check = repositoryService.saveRepository(repository);
        if (check == 1) {
            json = new JsonResult(JsonResult.SUCCESS, "插入成功");
        } else if(check==2) {
            json = new JsonResult(JsonResult.FAILURE, "插入失败,有相同的仓库名称或者仓库编号");
        }
        else{
            json = new JsonResult(JsonResult.FAILURE, "插入失败,插入失败");
        }
        json.toJson(res);
    }
}
