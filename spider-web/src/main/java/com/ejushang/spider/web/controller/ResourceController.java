package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.domain.Resource;
import com.ejushang.spider.service.permission.IPermissionService;
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
 * Date: 13-12-30
 * Time: 下午3:24
 * 模块控制
 */
@Controller
@RequestMapping(value="/resource")
public class ResourceController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IResourceService resourceService;

//    /**
//     * 添加一个模块
//     * @param response
//     * @param
//     * @throws IOException
//     */
//    @RequestMapping(value="/add")
//    public void addRole(HttpServletResponse response)throws IOException {
//
//    }
//
//    /**
//     * 保存
//     * @param response
//     * @param resource
//     * @throws IOException
//     */
//    @RequestMapping(value="/save")
//    public void saveRole(HttpServletResponse response,Resource resource)throws IOException {
//        resourceService.save(resource);
//        new JsonResult(JsonResult.SUCCESS,"添加成功！").toJson(response);
//    }

    /**
     * 查询所有模块
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/list")
    public void findAll(HttpServletResponse response)throws IOException {
        if(log.isInfoEnabled()){
            log.info("查询出所有模块resource");
        }
        List<Resource> resourceList=resourceService.findAll();
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,resourceList).toJson(response);
    }

//    /**
//     * 修改
//     * @param response
//     * @throws IOException
//     */
//    @RequestMapping(value="/update")
//    public void updateResource(HttpServletResponse response,Resource resource)throws IOException {
//    }
//
//    /**
//     * 删除模块根据id
//     * @param response
//     * @param id
//     * @throws IOException
//     */
//    @RequestMapping(value="/delete")
//    public void delRole(HttpServletResponse response,Integer id)throws IOException{
//        resourceService.delete(id);
//        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
//    }
//
//    /**
//     * 删除多个模块根据id
//     * @param response
//     * @param ids
//     * @throws IOException
//     */
//    @RequestMapping(value="/deleteMore")
//    public void delMoreRole(HttpServletResponse response,int[] ids)throws IOException{
//        resourceService.deleteMoreResource(ids);
//        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
//    }

}
