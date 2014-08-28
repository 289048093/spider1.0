
package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.User;

import java.util.List;

import com.ejushang.spider.domain.User;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: liubin
 * Date: 13-12-13
 */
public interface UserMapper {

	User findUserById(int id);

	int save(User user);

	int  update(User user);

	int  delete(int id);

    List<User> findRootUser();

    List<User> findByRole(int roleId);

    User findUserByUsername(String username);

    List<User> findAllUser();

    List<User> findPageUser(@Param("username")String username,@Param("page")Page page);

    void updateTime(Integer id);

}

