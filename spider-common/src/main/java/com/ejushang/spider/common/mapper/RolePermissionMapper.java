package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.RolePermission;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-13
 */
public interface RolePermissionMapper {

	RolePermission get(int id);

	void save(RolePermission rolePermission);

	void update(RolePermission rolePermission);

	void delete(int id);

    void deleteByRole(int roleId);

    void deleteAll();

    void deleteByOperation(int operationId);

    List<RolePermission> findByRole(int roleId);
}
