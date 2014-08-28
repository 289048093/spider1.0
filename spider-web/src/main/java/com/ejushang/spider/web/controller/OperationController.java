package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.domain.Resource;
import com.ejushang.spider.service.permission.IResourceService;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User:moon
 * Date: 13-12-31
 * Time: 上午11:15
 */
@Controller
@RequestMapping(value="/operation")
public class OperationController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IResourceService resourceService;

//    /**
//     * 添加一个操作
//     * @param response
//     * @param
//     * @throws java.io.IOException
//     */
//    @RequestMapping(value="/add")
//    public void addRole(HttpServletResponse response)throws IOException {
//       List<Resource> resourceList= resourceService.findAll();
//        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,resourceList).toJson(response);
//    }
//
//    /**
//     * 保存操作
//     * @param response
//     * @param operation
//     * @throws IOException
//     */
//    @RequestMapping(value="/save")
//    public void saveRole(HttpServletResponse response,Operation operation)throws IOException {
//        resourceService.saveOperation(operation);
//        new JsonResult(JsonResult.SUCCESS,"添加成功！").toJson(response);
//    }

    /**
     * 查询所有操作
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/list")
    public void findAll(HttpServletResponse response)throws IOException {
        if(log.isInfoEnabled()){
               log.info("查询出所有操作operation");
        }
        List<Operation> operationList=resourceService.findAllOperation();
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,operationList).toJson(response);
    }

//    /**
//     * 修改
//     * @param response
//     * @throws IOException
//     */
//    @RequestMapping(value="/update")
//    public void updateResource(HttpServletResponse response,Operation operation)throws IOException {
//         resourceService.updateOperation(operation);
//         new JsonResult(JsonResult.SUCCESS,"修改成功！").toJson(response);
//    }
//
//    /**
//     * 删除操作根据id
//     * @param response
//     * @param id
//     * @throws IOException
//     */
//    @RequestMapping(value="/delete")
//    public void delRole(HttpServletResponse response,Integer id)throws IOException{
//        resourceService.deleteOperation(id);
//        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
//    }
//
//    /**
//     * 删除多个操作根据id
//     * @param response
//     * @param ids
//     * @throws IOException
//     */
//    @RequestMapping(value="/deleteMore")
//    public void delMoreRole(HttpServletResponse response,int[] ids)throws IOException{
//        resourceService.deleteMoreOperation(ids);
//        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
//    }
}
