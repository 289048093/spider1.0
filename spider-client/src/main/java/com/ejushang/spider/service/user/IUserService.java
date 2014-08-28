package com.ejushang.spider.service.user;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.util.Page;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-23
 */
public interface IUserService {
    /**
     * 查找系统里的超级用户
     * @return
     */
    List<User> findRootUser();

    /**
     * 直接保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 根据角色ID查找用户
     * @param roleId
     * @return
     */
    List<User> findByRole(int roleId);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    public void deleteUser(int id);

    public void updateUser(User user);

    public User selectUserById(int id);

    public List<User> findAllUser();

    public Page findPageUser(String username,Integer page1,Integer limit);

    public User findUserById(int id);

    /**
     *查询出所有的角色，根据用户的id标记出该用户所具有的角色
     * @param id
     * @return
     */
    public List<Role> findUserRole(Integer id);

    /**
     * 批量删除用户
     */
    void deleteMoreUser(int[] idArray);

    /**
     * 登录成功之后调用的方法
     * @param user
     */
    void doAfterLoginSuccess(User user);
}
