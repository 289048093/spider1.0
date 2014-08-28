package com.ejushang.spider.util;

import com.ejushang.spider.domain.User;

/**
 * User: Blomer
 * Date: 14-1-18
 * Time: 下午3:57
 */
public class CurrentUserStore {

    private static ThreadLocal<User>  userThreadLocal = new ThreadLocal<User>();

    public static void setCurrentUser(User user){
        userThreadLocal.set(user);
    }

    public static User getCurrentUser(){
        return userThreadLocal.get();
    }

}
