package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.Resource;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-13
 */
public interface ResourceMapper {

	Resource get(int id);

	void save(Resource resource);

	void update(Resource resource);

	void delete(int id);

	List<Resource> findAll();
	
}
