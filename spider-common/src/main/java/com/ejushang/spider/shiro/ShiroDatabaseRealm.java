package com.ejushang.spider.shiro;

import com.ejushang.spider.domain.Permission;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.service.permission.PermissionService;
import com.ejushang.spider.service.permission.ResourceService;
import com.ejushang.spider.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by liubin on 13-12-16.
 */
public class ShiroDatabaseRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


	/**
	 * 用户登录的认证方法
	 *
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordCaptchaToken usernamePasswordToken = (UsernamePasswordCaptchaToken) token;

        // 判断验证码
        String captcha = usernamePasswordToken.getCaptcha();
        Session session = SecurityUtils.getSubject().getSession();
        String captchaInSession = (String)session.getAttribute("captcha");
        session.setAttribute("captcha", null);
        if (null == captcha || !captcha.equalsIgnoreCase(captchaInSession)) {
            throw new AuthenticationException("验证码错误");
        }

		String username = usernamePasswordToken.getUsername();

		if (username == null) {
			throw new AccountException("用户名不能为空");
		}

		User user = userService.findUserByUsername(username);

		if (user == null) {
			throw new UnknownAccountException("用户不存在");
		}

        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

    /**
     *
     * 当用户进行访问链接时的授权方法
     *
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }

        User user = (User)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addObjectPermissions((Collection<org.apache.shiro.authz.Permission>)user.getPermissionsCache().values());

        return info;
    }

    private boolean checkIfRootUser(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        return (user != null && user.isRootUser());
    }

    //以下方法都是为root用户服务,判断为root直接跳过验证

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if(checkIfRootUser(principals)) {
            return true;
        }
        return super.isPermitted(principals, permission);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, org.apache.shiro.authz.Permission permission) {
        if(checkIfRootUser(principals)) {
            return true;
        }
        return super.isPermitted(principals, permission);
    }

    @Override
    public boolean[] isPermitted(PrincipalCollection subjectIdentifier, String... permissions) {
        if(permissions == null || permissions.length == 0) {
            return null;
        }
        if(checkIfRootUser(subjectIdentifier)) {
            boolean[] results = new boolean[permissions.length];
            Arrays.fill(results, true);
            return results;
        }
        return super.isPermitted(subjectIdentifier, permissions);
    }

    @Override
    public boolean[] isPermitted(PrincipalCollection principals, List<org.apache.shiro.authz.Permission> permissions) {
        if(permissions == null || permissions.isEmpty()) {
            return null;
        }
        if(checkIfRootUser(principals)) {
            boolean[] results = new boolean[permissions.size()];
            Arrays.fill(results, true);
            return results;
        }
        return super.isPermitted(principals, permissions);
    }

    @Override
    public boolean isPermittedAll(PrincipalCollection subjectIdentifier, String... permissions) {
        if(checkIfRootUser(subjectIdentifier)) {
            return true;
        }
        return super.isPermittedAll(subjectIdentifier, permissions);
    }

    @Override
    public boolean isPermittedAll(PrincipalCollection principal, Collection<org.apache.shiro.authz.Permission> permissions) {
        if(checkIfRootUser(principal)) {
            return true;
        }
        return super.isPermittedAll(principal, permissions);
    }

    @Override
    public void checkPermission(PrincipalCollection subjectIdentifier, String permission) throws AuthorizationException {
        if(checkIfRootUser(subjectIdentifier)) {
            return;
        }
        super.checkPermission(subjectIdentifier, permission);
    }

    @Override
    public void checkPermission(PrincipalCollection principal, org.apache.shiro.authz.Permission permission) throws AuthorizationException {
        if(checkIfRootUser(principal)) {
            return;
        }
        super.checkPermission(principal, permission);
    }

    @Override
    public void checkPermissions(PrincipalCollection subjectIdentifier, String... permissions) throws AuthorizationException {
        if(checkIfRootUser(subjectIdentifier)) {
            return;
        }
        super.checkPermissions(subjectIdentifier, permissions);
    }

    @Override
    public void checkPermissions(PrincipalCollection principal, Collection<org.apache.shiro.authz.Permission> permissions) throws AuthorizationException {
        if(checkIfRootUser(principal)) {
            return;
        }
        super.checkPermissions(principal, permissions);
    }

}
