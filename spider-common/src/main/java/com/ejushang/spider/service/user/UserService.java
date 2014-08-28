package com.ejushang.spider.service.user;

import com.ejushang.spider.domain.Permission;
import com.ejushang.spider.domain.Role;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.common.mapper.UserMapper;
import com.ejushang.spider.service.permission.*;
import com.ejushang.spider.shiro.UrlPermission;
import com.ejushang.spider.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * User: liubin
 * Date: 13-12-13
 */
@Service
public class UserService implements IUserService {

	@javax.annotation.Resource
	private UserMapper userMapper;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleService roleService;

    RandomNumberGenerator rng = new SecureRandomNumberGenerator();

    /**
     * 查询超级用户
     * @return 用户
     */
    @Override
    @Transactional(readOnly = true)
	public List<User> findRootUser() {
		return userMapper.findRootUser();
	}

    /**
     * 保存用户
     * @param user
     */

    @Override
    @Transactional
	public void saveUser(User user) {
        if(user.getId() == null || user.getId() == 0) {
            String salt = rng.nextBytes().toBase64();
            user.setPassword(new Sha256Hash(user.getPassword(), salt).toBase64());
            user.setSalt(salt);
            user.setCreateTime(new Date());
            List<String> strings=new ArrayList<String>();
            List<User> userList=userMapper.findAllUser();
            for(User user1:userList){
                strings.add(user1.getUsername());
            }
            if(strings.contains(user.getUsername())) {
                throw new IllegalStateException(String.format("用户[%s]已经存在,不能重复添加", user.getUsername()));
            }
            userMapper.save(user);
        } else {
            userMapper.update(user);
        }
	}

    /**
     * 根据拥有该角色的用户
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findByRole(int id) {
        return userMapper.findByRole(id);
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        if(StringUtils.isBlank(username)) {
            return null;
        }
        return userMapper.findUserByUsername(username);
    }

    /**
     * 根据用户id删除用户
     * @param id
     */
    @Override
    @Transactional
    public void deleteUser(int id) {

        userMapper.updateTime(id);
        userMapper.delete(id);
    }

    /**
     *修改用户
     * @param user
     */
    @Override
    public void updateUser(User user) {
        if(!user.getPassword().equals("")){
            String salt = rng.nextBytes().toBase64();
            user.setPassword(new Sha256Hash(user.getPassword(), salt).toBase64());
            user.setSalt(salt);
        }
        userMapper.update(user);

    }

    /**
     * 查询用户根据id
     * @param id
     * @return
     */
    @Override
    public User selectUserById(int id) {
        return   userMapper.findUserById(id);
    }

    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public Page findPageUser(String username,Integer page1,Integer limit) {
        Page page=new Page();
        page.setPageNo(page1);
        page.setPageSize(limit);
        List<User> list=userMapper.findPageUser(username,page);
        page.setResult(list);
        return page;
    }

    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<User> findAllUser() {
        List<User> list=userMapper.findAllUser();
        return list;
    }

    /**
     * 根据id查询出用户
     * @param id
     * @return
     */
    @Override
    public User findUserById(int id) {
        return  userMapper.findUserById(id);
    }

    /**
     *查询出所有的角色，根据用户的id标记出该用户所具有的角色
     * @param id
     * @return
     */
    @Override
    @Transactional
    public List<Role> findUserRole(Integer id) {

            //查询出所有的角色
            List<Role> roleList=roleService.findAllRole();
            //根据id查询出用户
            User user=userMapper.findUserById(id);
            //查询出一个用户所拥有的角色
            Role role=roleService.findRoleById(user.getRoleId());

            for(Role r:roleList){
                if(r.equals(role)){
                    r.setUR(true);
                }
            }

        return roleList;
    }

    /**
     * 删除多个用户
     * @param idArray
     */
    @Override
    @Transactional
    public void deleteMoreUser(int[] idArray) {
        for(int id:idArray){

            userMapper.updateTime(id);
            deleteUser(id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void doAfterLoginSuccess(User user) {
        if(!user.isRootUser()) {
            //缓存用户权限
            List<Permission> permissions = permissionService.findByRole(user.getRoleId());
            for(Permission permission : permissions) {
                permission.setOperation(resourceService.getOperationById(permission.getOperationId()));
            }
            Map<String, UrlPermission> permissionsCache = new HashMap<String, UrlPermission>();
            for(Permission p : permissions) {
                String url = p.getOperation().getUrl();
                permissionsCache.put(url, new UrlPermission(url));
            }
            user.setPermissionsCache(permissionsCache);

        }

        //缓存用户角色
        user.setRole(roleService.findRoleById(user.getRoleId()));
    }
}
