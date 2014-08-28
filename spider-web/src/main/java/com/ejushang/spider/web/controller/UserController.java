package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.service.permission.IRoleService;
import com.ejushang.spider.service.user.IUserService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.web.util.JsonResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * User:moon
 * Date: 13-12-27
 * Time: 下午1:18
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    /**
     * 查询所有用户
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/list")
    public void findAllUser(String userName,Integer page,Integer limit,HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("查询出所有用户");
        }
        Page pages=userService.findPageUser(userName,page,limit);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,pages).toJson(response);
    }

    /**
     * 修改用户
     * @param response
     * @param user
     * @throws IOException
     */
    @RequestMapping(value="/update")
    public void updateUser(HttpServletResponse response,User user) throws IOException {

        if (log.isInfoEnabled()) {
            log.info("修改用户，用户为:"+user);
        }
        userService.updateUser(user);
        new JsonResult(JsonResult.SUCCESS,"修改成功！").toJson(response);
    }

    /**
     * 添加 ，先查询出所有的角色
     * @param response
     * @param
     * @throws IOException
     */
    @RequestMapping(value="/add")
    public void addUser(HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("跳转到添加用户页面");
        }
       List<Role> roleList= roleService.findAllRole();
       new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,roleList).toJson(response);
    }

    /**
     * 保存
     * @param response
     * @param user
     * @throws IOException
     */
    @RequestMapping(value="/save")
    public void saveUser(HttpServletResponse response,User user) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("添加用户，用户为:"+user);
        }
        try{
        userService.saveUser(user);
        new JsonResult(JsonResult.SUCCESS,"添加成功！").toJson(response);
        }catch (Exception e){
            new JsonResult(JsonResult.FAILURE,e.getMessage()).toJson(response);
        }
    }

    /**
     * 查询出所有的角色并标记该用户所具有的的角色
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping(value="/view")
    public void findUserRole(HttpServletResponse response,Integer id) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("查询出所有的角色并标记该用户所具有的的角色，用户id:"+id);
        }
       List<Role> roleList= userService.findUserRole(id);
        User user=userService.findUserById(id);
        Role role=roleService.findRoleById(user.getRoleId());
        if(!role.getName().equals("仓库人员")){
            user.setRepoId(0);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST,roleList)
                                          .addData("repoId",user.getRepoId()).toJson(response);
    }

    /**
     * 删除用户根据id
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping(value="/delete")
    public void delRole(HttpServletResponse response,Integer id)throws IOException{
        if (log.isInfoEnabled()) {
            log.info("删除用户，用户id:"+id);
        }
        userService.deleteUser(id);
        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
    }

    /**
     * 删除多个用户根据id
     * @param response
     * @param idArray
     * @throws IOException
     */
    @RequestMapping(value="/deleteMore")
    public void delMoreRole(HttpServletResponse response,int[] idArray)throws IOException{
        if (log.isInfoEnabled()) {
            log.info("删除多个用户，用户id:"+idArray);
        }
        userService.deleteMoreUser(idArray);
        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
    }
}
