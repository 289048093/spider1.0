package com.ejushang.spider.service.permission;

import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.domain.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-23
 */
public interface IResourceService {

    /**
     * 直接保存资源
     * @param resource
     */
    void save(Resource resource);

    /**
     * 直接保存操作
     * @param operation
     */
    void saveOperation(Operation operation);

    /**
     * 根据ID查询资源
     * @param id
     * @return
     */
    Resource get(int id);

    /**
     * 根据ID删除资源
     * @param id
     */
    void delete(int id);

    /**
     * 查询所有资源
     * @return
     */
    List<Resource> findAll();

    /**
     * 查询所有操作
     * @return
     */
    List<Operation> findAllOperation();

    /**
     * 根据ID查找操作
     * @param operationId
     * @return
     */
    Operation getOperationById(int operationId);

    /**
     * 根据资源ID查找操作
     * @param resourceId
     * @return
     */
    List<Operation> findOperationByResource(Integer resourceId);

    /**
     * 删除多个
     */
    void deleteMoreResource(int[] ids);

    /**
     * 根据id删除操作
     */
    void deleteOperation(Integer id);

    /**
     * 批量删除操作
     */
    void deleteMoreOperation(int[] ids);

    /**
     * 修改一个操作
     */
    void updateOperation(Operation operation);

    /**
     * 根据url查找操作
     * @param operationUrl
     */
    Operation getOperationByUrl(String operationUrl);
}
