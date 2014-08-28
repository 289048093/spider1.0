
package com.ejushang.spider.service.permission;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.util.Page;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-23
 */

public interface IRoleService {

    String ADMINISTRATOR_ROLE_NAME = "超级管理员";

    /**
     * 根据名称查找角色
     * @param name
     * @return
     */
    Role findRoleByName(String name);

    /**
     * 直接保存角色
     * @param role
     */
    void saveRole(Role role);

    /**
     * 查找所有角色
     * @return
     */
    List<Role> findAllRole();

    /**
     * 删除角色
     * @param id
     */
    void deleteRoleById(Integer id);

    /**
     * 根据操作ID查找角色
     * @param operationId
     * @return
     */
    List<Role> findRoleByOperation(int operationId);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    Role findRoleById(int id);

    /**
     * 修改角色
     * @param role
     */
    public void updateRole(Role role);

    /**
     * 批量删除
     */
    void deleteMoreRole(int[] idArray);

    public Page findPageRole(String name,Integer page,Integer limit);
}

