package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.Operation;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-13
 */
public interface OperationMapper {

	Operation get(int id);

	void save(Operation operation);

	void update(Operation operation);

	void delete(int id);

	List<Operation> findAll();

    List<Operation> findByResource(int resourceId);

    Operation findOperationByName(String name);

    Operation getByUrl(String url);
}
