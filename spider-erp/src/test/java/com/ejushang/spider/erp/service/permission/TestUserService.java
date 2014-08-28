package com.ejushang.spider.erp.service.permission;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.service.permission.IRoleService;
import com.ejushang.spider.service.user.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * User:moon
 * Date: 13-12-23
 * Time: 下午2:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-erp.xml")
public class TestUserService {


    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Test
    public void insertUser(){
        User user=new User();
        user.setUsername("赵六");
        user.setPassword("111");
        user.setSalt("11");
        user.setRoleId(1);
        userService.saveUser(user);
        System.out.println("=================================" + user.getId());
    }
    @Test
    public void selectAllUser(){
       List<User> list=  userService.findAllUser();
        for(User l:list){
            System.out.println(l.getId()+"/"+l.getUsername()+"/"+l.getPassword());
        }
    }

    @Test
    public void delUser(){
        User user= userService.findUserById(2);
        userService.deleteUser(2);

        System.out.println("====================ok!");
        System.out.println(user.getDeleteTime());

    }

    @Test
    public void updateUser(){
        User user=new User();
        user=userService.selectUserById(2);
        user.setUsername("李四");
        user.setPassword("222");
        user.setSalt("22");
       user.setRoleId(2);
        user.setEmail("11111111@163.com");
        userService.updateUser(user);
        System.out.println("====================ok!");
        System.out.println("=================================" + user.getId());
        // User user1= userService.selectUserById(11) ;
        //  Assert.assertEquals("错误", user, user1);
    }

    //@Test
    public void selectUserById(){
        userService.selectUserById(10);
        System.out.println("====================ok!");
    }

    @Test
    public void findUserRole(){
      //查询出所有的角色
        List<Role> roleList=roleService.findAllRole();
        //根据id查询出用户
        User user=userService.findUserById(12);
      //查询出一个用户所拥有的角色
      Role role=roleService.findRoleById(user.getRoleId());

       for(Role r:roleList){
          if(r.equals(role)){
              r.setUR(true);
          }
          System.out.println(r.getId()+"/"+r.getName()+"/"+r.isUR());
       }

    }

}
