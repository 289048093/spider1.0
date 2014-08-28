
package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.Role;

import java.util.List;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-13
 */
public interface RoleMapper {

	int  save(Role role);

    int update(Role role);

    int delete(int id);

    Role findRoleById(int id);

	Role findRoleByName(String name);

	List<Role> findAllRole();

    List<Role> findPageRole(@Param("name")String name,@Param("page")Page page);

    /** 根据操作id查询出拥有该操作权限的角色*/
    List<Role> findRoleByOperation(int operationId);
}
