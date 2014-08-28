package com.ejushang.spider.erp.service.permission;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.service.permission.IPermissionService;
import com.ejushang.spider.service.permission.IRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User:moon
 * Date: 13-12-23
 * Time: 下午4:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-erp.xml")
public class TestRoleService {

    @Resource(name="roleService")
    private IRoleService roleService;

    @Resource
    private IPermissionService permissionService;


    // @Test
    public void insertRole(){
        Role role=new Role();
        role.setName("总经理");
        roleService.saveRole(role);
        System.out.println("====================="+role.getId());
    }

    // @Test
    public void updateRole(){
        Role role=roleService.findRoleById(1);

        //role.setName("总经理助理");

        roleService.updateRole(role);
        System.out.println("====================ok!");
    }

    // @Test
    public void findRoleById(){
        Role role=new Role();
        role= roleService.findRoleById(10);
        System.out.println(role.getId()+"/"+role.getName());
        System.out.println("====================ok!");
    }

     @Test
    public void delRole(){
        Role role=new Role();
        role=roleService.findRoleById(19);
        roleService.deleteRoleById(19);
        System.out.println("====================ok!");
    }

    //@Test
    public void findAllRole(){
        List<Role> list=new ArrayList<Role>();
        list=roleService.findAllRole();
        for(Role r:list){
            System.out.println(r.getId()+"/"+r.getName());
        }
    }

    // @Test
    public void findRoleByName(){
        Role role=new Role();
        role= roleService.findRoleByName("超级管理员") ;
        System.out.println(role.getId()+"/"+role.getName());
        System.out.println("====================ok!");
    }

     @Test
    public void findRoleByOperation(){
        // public List<Role> findRoleByOperation(int operationId) {
        List<Role> list=new ArrayList<Role>();
        list=roleService.findRoleByOperation(7);
        for(Role r:list){
            System.out.println(r.getId()+"/"+r.getName());
        }
    }

    @Test
    public void delMoreRole(){
        int[] ids={17,20};
        roleService.deleteMoreRole(ids);
    }

    @Test
    public void testChangeOp(){
       permissionService.changeOp(34);
    }
}
