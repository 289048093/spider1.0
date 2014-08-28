package com.ejushang.spider.shiro;

import com.ejushang.spider.domain.User;
import com.ejushang.spider.service.user.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户登录登出Listener
 * User: liubin
 * Date: 14-1-16
 */
public class LoginLogoutActionListener implements AuthenticationListener {

    private static final Logger log = LoggerFactory.getLogger(LoginLogoutActionListener.class);

    @Autowired
    private IUserService userService;

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        log.info("用户[{}]登录成功", ((UsernamePasswordToken) token).getUsername());
        User user = (User)info.getPrincipals().getPrimaryPrincipal();
        userService.doAfterLoginSuccess(user);

    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        log.info("用户[{}]登录失败", ((UsernamePasswordToken) token).getUsername());
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        if(user == null) {
            log.info("用户已注销");
        } else {
            log.info("用户[{}]已注销", user.getUsername());
        }
    }

}
