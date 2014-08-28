package com.ejushang.spider.service.permission;

import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.domain.Resource;
import com.ejushang.spider.common.mapper.OperationMapper;
import com.ejushang.spider.common.mapper.ResourceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * User: liubin
 * Date: 13-12-13
 */
@Service
public class ResourceService implements IResourceService {

	@javax.annotation.Resource
	private ResourceMapper resourceMapper;

	@javax.annotation.Resource
	private OperationMapper operationMapper;

    /**
     * 保存资源
     * @param resource
     */
	@Override
    @Transactional
	public void save(Resource resource) {
        if(resource.getId() == null || resource.getId() == 0) {
            resourceMapper.save(resource);
        } else {
            resourceMapper.update(resource);
        }
	}

    /**
     *保存操作
     * @param operation
     */
    @Override
    @Transactional
    public void saveOperation(Operation operation) {
        if(operation.getId() == null || operation.getId() == 0) {
            operationMapper.save(operation);
        } else {
            operationMapper.update(operation);
        }
    }

    /**
     *查询资源
     * @param id
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public Resource get(int id) {
		return resourceMapper.get(id);
	}

    /**
     *删除资源
     * @param id
     */
	@Override
    @Transactional
	public void delete(int id) {
		resourceMapper.delete(id);
	}

    /**
     *查询所有资源
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public List<Resource> findAll() {
		return resourceMapper.findAll();
	}

    /**
     *查询出所有操作
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public List<Operation> findAllOperation() {
	List<Operation> list= operationMapper.findAll();
        for(Operation l:list){
            l.setResourceName(resourceMapper.get(l.getResourceId()).getName());
        }
        return list;
	}

    /**
     *通过id查询出操作
     * @param operationId
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public Operation getOperationById(int operationId) {
		return operationMapper.get(operationId);
	}

    /**
     *通过资源id找到操作
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Operation> findOperationByResource(Integer resourceId) {
        return operationMapper.findByResource(resourceId);
    }

    /**
     *删除多个资源
     * @param ids
     */
    @Override
    public void deleteMoreResource(int[] ids) {
        for(int id:ids){
            resourceMapper.delete(id);
        }
    }

    /**
     *删除操作
     * @param id
     */
    @Override
    public void deleteOperation(Integer id) {
       operationMapper.delete(id);
    }

    /**
     *删除多个操作
     * @param ids
     */
    @Override
    public void deleteMoreOperation(int[] ids) {
        for(int id:ids){
            operationMapper.delete(id);
        }
    }

    /**
     *修改操作
     * @param operation
     */
    @Override
    public void updateOperation(Operation operation) {
        operationMapper.update(operation);
    }

    @Transactional(readOnly = true)
    @Override
    public Operation getOperationByUrl(String operationUrl) {
        return operationMapper.getByUrl(operationUrl);
    }
}
