package com.ejushang.spider.web.controller;

import com.ejushang.spider.common.mapper.OperationMapper;
import com.ejushang.spider.common.mapper.PermissionMapper;
import com.ejushang.spider.common.mapper.RolePermissionMapper;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.service.permission.IPermissionService;
import com.ejushang.spider.service.permission.IResourceService;
import com.ejushang.spider.service.permission.IRoleService;
import com.ejushang.spider.service.permission.RoleService;
import com.ejushang.spider.service.user.IUserService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.ResourceVo;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * 角色控制
 * User:moon
 * Date: 13-12-26
 * Time: 上午10:27
 */
@Controller
@RequestMapping(value="/role")
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IUserService userService;



    /**
     * 添加角色，显示所有的模块操作
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/add")
    public void addRole(HttpServletResponse response)throws IOException{
        if(log.isInfoEnabled()){
            log.info("跳转到添加角色的页面");
        }
        List<Resource> listResource=permissionService.addAllPermission();
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,listResource).toJson(response);
    }

    /**
     * 保存角色，显示所有的模块操作，并显示该角色所拥有的操作
     * @param response
     * @param roleName
     * @param permissionId
     * @throws IOException
     */
    @RequestMapping(value="/save")
    public void saveRole(HttpServletResponse response,String roleName,int[] permissionId)throws IOException{
        if (log.isInfoEnabled()) {
            log.info("添加角色，角色名：" + roleName + " 权限id为:" + permissionId);
        }
        Role role=new Role();
        role.setName(roleName);
        try{
        roleService.saveRole(role);
        if (log.isInfoEnabled()) {
            log.info("保存的role" + role.toString());
        }
        Collection<String> names= permissionService.updateStringList(permissionId);
        permissionService.grantPermissions(role.getId(),names);

        new JsonResult(JsonResult.SUCCESS,"添加成功！").toJson(response);
       }catch (Exception e){
            new JsonResult(JsonResult.FAILURE,e.getMessage()).toJson(response);
        }
    }

    /**
     * 查询所有角色
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/list")
    public void findAllRole(String name,Integer page,Integer limit,HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("查询所有角色 ");
        }
        Page pages=roleService.findPageRole(name, page, limit);
       new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,pages).toJson(response);
    }

    /**
     * 删除角色根据id
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping(value="/delete")
    public void delRole(HttpServletResponse response,Integer id)throws IOException{

        if (log.isInfoEnabled()) {
            log.info("删除角色，角色id：" +id);
        }
        roleService.deleteRoleById(id);
        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
    }

    /**
     * 删除多个角色根据id
     * @param response
     * @param idArray
     * @throws IOException
     */
    @RequestMapping(value="/deleteMore")
    public void delMoreRole(HttpServletResponse response,int[] idArray)throws IOException{
        if (log.isInfoEnabled()) {
            log.info("删除多个角色，角色id：" +idArray);
        }
        try{
        roleService.deleteMoreRole(idArray);
            new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
        }catch (Exception e){
            new JsonResult(JsonResult.FAILURE,e.getMessage()).toJson(response);
        }
    }

    /**
     * 修改角色
     * @param response
     * @param role
     * @throws IOException
     */
    @RequestMapping(value="/update")
    public void updateRole(HttpServletResponse response,Role role)throws IOException{

        if (log.isInfoEnabled()) {
            log.info("修改角色，角色为：" +role);
        }
        roleService.updateRole(role);
        new JsonResult(JsonResult.SUCCESS).toJson(response);
    }

    /**
     * 查询出所有模块和操作及显示该角色所拥有的操作根据角色的id
     * @param response
     * @param roleId
     * @throws IOException
     */
    @RequestMapping(value="/permission/get")
    public void findAllResource(HttpServletResponse response,Integer roleId)throws IOException{

        if (log.isInfoEnabled()) {
            log.info("查询出所有模块和操作及显示该角色所拥有的操作根据角色的id，角色id：" +roleId);
        }
        List<Resource> listResource=permissionService.findAllPermission(roleId);
       new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,listResource).toJson(response);
    }

    /**
     * 给角色授权 根据角色id和操作id
     * @param response
     * @param roleId
     * @param permissionId
     * @throws IOException
     */
    @RequestMapping(value="/permission/save")
    public void saveRolePermission(HttpServletResponse response,Integer roleId,int[] permissionId)throws IOException{

        if (log.isInfoEnabled()) {
            log.info("给角色授权，角色id：" +roleId+"添加的权限id"+permissionId);
        }
             permissionService.changeOp(roleId);
             Collection<String> names= permissionService.updateStringList(permissionId);
             permissionService.grantPermissions(roleId,names);
             new JsonResult(JsonResult.SUCCESS,"授权成功！").toJson(response);
    }

    @RequestMapping(value="/permission/link")
    public void findLinkOperation(HttpServletResponse response,Integer id,String status) throws IOException{
        if (log.isInfoEnabled()) {
            log.info("给角色授权时查询相关联的操作，接收到的操作id：" +id);
        }
        List<Integer> integerList=permissionService.findLinkOperation(id,status);

        try {
            new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,integerList).toJson(response);

        } catch (IOException e) {
            if(log.isErrorEnabled()){
                    log.info("查询错误！",e);
            }
            new JsonResult(JsonResult.FAILURE,"查询错误！").toJson(response);
        }
    }

    /**
     * 初始化用户的操作桌面
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping(value="/user/resource")
    public void findUserResource(HttpServletResponse response,Integer id) throws IOException{
        if (log.isInfoEnabled()) {
            log.info("初始化用户的操作桌面，接收到的操作id：" +id);
        }
        List<ResourceVo> resourceVoList=permissionService.findRoleResource(id);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,resourceVoList).toJson(response);
    }

}
