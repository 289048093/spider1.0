package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.Permission;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-13
 */
public interface PermissionMapper {

	Permission get(int id);

	void save(Permission permission);

	void update(Permission permission);
    /** 通过id删除操作权限*/
	void delete(int id);

    void deleteByOperation(int operationId);

    List<Permission> findByOperationName(String[] operationNames);

    List<Permission> findByRole(int roleId);
}
