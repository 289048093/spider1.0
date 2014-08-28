package com.ejushang.spider.service.permission;

import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.domain.Permission;
import com.ejushang.spider.domain.Resource;
import com.ejushang.spider.domain.RolePermission;
import com.ejushang.spider.vo.ResourceVo;

import java.util.Collection;
import java.util.List;

/**
 * User: liubin
 * Date: 13-12-23
 */
public interface IPermissionService {

    /**
     * 重新读取资源权限配置
     * @param fileName
     * @param updateRoles
     */
    boolean refreshPermissions(String fileName, boolean updateRoles);

    /**
     * 删除所有角色权限
     */
    void deleteAllRolePermissions();

    /**
     * 删除资源
     * @param resource
     */
    void deleteResource(Resource resource);

    /**
     * 删除操作
     * @param operation
     */
    void deleteOperation(Operation operation);

    /**
     * 对角色授权
     * @param roleId
     * @param operationNames 操作名称
     */
    void grantPermissions(Integer roleId, Collection<String> operationNames);

    /**
     * 直接保存权限
     * @param permission
     */
    void save(Permission permission);

    /**
     * 直接保存角色权限
     * @param rolePermission
     */
    void saveRolePermission(RolePermission rolePermission);

    /**
     * 查找角色下的所有权限
     * @param roleId
     * @return
     */
    List<Permission> findByRole(int roleId);

    /**
     * 查询出所有模块和操作及显示该角色所拥有的操作根据角色的id
     */
    List<Resource> findAllPermission(Integer roleId);

    /**
     *   根据permissionIds查找出相应的名字
     */
    Collection<String> updateStringList(int[] permissionId);

    public void changeOp(Integer roleId);

    /**
     * 根据id查询出该角色所具有的权限的模块
     * @return
     */
    public List<ResourceVo> findRoleResource(Integer id);

    /**
     * 一个角色拥有的操作
     */
    public List<Operation> findRoleOperate(Integer roleId);

    /**
     * 根据操作id查找出与它相关联的的操作的id
     */
    public List<Integer> findLinkOperation(Integer id,String status);

    public List<Resource> addAllPermission();
}

