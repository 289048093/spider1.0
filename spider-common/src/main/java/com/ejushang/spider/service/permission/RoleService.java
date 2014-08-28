
package com.ejushang.spider.service.permission;

import com.ejushang.spider.domain.Role;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.common.mapper.RoleMapper;
import com.ejushang.spider.common.mapper.RolePermissionMapper;
import com.ejushang.spider.service.user.UserService;
import com.ejushang.spider.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * User: liubin
 * Date: 13-12-13
 */
@Service
public class RoleService implements IRoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

	@javax.annotation.Resource
	private RoleMapper roleMapper;

    @javax.annotation.Resource
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserService userService;

    /**
     *查询角色根据名字
     * @param name
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public Role findRoleByName(String name) {
        if(StringUtils.isBlank(name)) return null;
        return roleMapper.findRoleByName(name);
	}

    /**
     *保存角色
     * @param role
     */
	@Override
    @Transactional
	public void saveRole(Role role) {

        if(role.getId() == null || role.getId() == 0) {
            List<String> strings=new ArrayList<String>();
            List<Role> roleList=roleMapper.findAllRole();
            for(Role role1:roleList){
                strings.add(role1.getName());
            }
            if(strings.contains(role.getName())) {
                throw new IllegalStateException(String.format("role[%s]已经存在,不能重复添加", role.getName()));
            }
            roleMapper.save(role);
            if (log.isInfoEnabled()) {
                log.info("保存的role" + role.toString());
            }
        } else {
            roleMapper.update(role);
        }
	}

    /**
     *查询所有的角色
     * @return
     */
	@Override
    public List<Role> findAllRole() {
		return roleMapper.findAllRole();
	}

    /**
     *查询所有的角色
     * @return
     */
    @Override
    public Page findPageRole(String name,Integer page,Integer limit) {
        Page pages=new Page();
        pages.setPageNo(page);
        pages.setPageSize(limit);
       List<Role> roleList= roleMapper.findPageRole(name,pages);
        pages.setResult(roleList);
        return pages;
    }

    /**
     * 删除角色根据id
     * @param id
     */
    @Override
    @Transactional
    public void deleteRoleById(Integer id) {

        List<User> users = userService.findByRole(id);
        if(!users.isEmpty()) {
            throw new IllegalStateException(String.format("role[%s]下有%d个用户,不能删除", id, users.size()));
        }
        rolePermissionMapper.deleteByRole(id);
        roleMapper.delete(id);

    }

    /**
     * 查询角色根据操作id
     * @param operationId
     * @return
     */
    @Override
    public List<Role> findRoleByOperation(int operationId) {
        return roleMapper.findRoleByOperation(operationId);
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        roleMapper.update(role);
    }

    /**
     * 查询角色根据id
     * @param id
     * @return
     */
    @Override
    public Role findRoleById(int id) {
        return roleMapper.findRoleById(id);
    }

    /**
     * 删除多个角色
     * @param idArray
     */
    @Override
    public void deleteMoreRole(int[] idArray) {
         for(int id:idArray){
           deleteRoleById(id);
         }
    }
}

