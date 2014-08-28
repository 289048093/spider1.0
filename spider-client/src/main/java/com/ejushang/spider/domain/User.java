package com.ejushang.spider.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Map;

/**
 * 用户
 */
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
    /** 用户名 */
	private String username;
    /** 真实姓名 */
    private String realName;
    /** 密码 */
	private String password;
    /** 盐 */
    private String salt;
    /** 是否超级用户 */
	private boolean rootUser = false;
    /** 角色ID */
	private Integer roleId;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 删除时间 */
    private Date deleteTime;

    /** 邮箱*/
    private String email;

    /** 电话*/
    private String telephone;

    /** 角色*/
    private Role role;

    /**仓库id */
    private Integer repoId;

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    //用户拥有的权限缓存,key为url,value为UrlPermission对象
    private Map<String, ? extends Object> permissionsCache;

    public void setPermissionsCache(Map<String, ? extends Object> permissionsCache) {
        this.permissionsCache = permissionsCache;
    }

    public Map<String, ? extends Object> getPermissionsCache() {
        return permissionsCache;
    }


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRootUser() {
		return rootUser;
	}

	public void setRootUser(boolean rootUser) {
		this.rootUser = rootUser;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("username", this.username)
                .append("id", this.id).toString();
    }


}
