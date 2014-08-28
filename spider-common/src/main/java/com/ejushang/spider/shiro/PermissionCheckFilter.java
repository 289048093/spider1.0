package com.ejushang.spider.shiro;

import com.ejushang.spider.util.JsonResult;
import com.ejushang.spider.util.JsonUtil;
import com.ejushang.spider.util.PermissionUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验url访问权限
 * User: liubin
 * Date: 13-12-16
 */
public class PermissionCheckFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request, response);
        String requestURI = getPathWithinApplication(request);
        return subject.isPermitted(new UrlPermission(PermissionUtils.trimUrlSuffix(requestURI)));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            HttpServletResponse resp = (HttpServletResponse) response;
            //判断没有权限,则返回授权失败的json信息
            String requestURI = getPathWithinApplication(request);
            String errorMsg = "您没有操作权限,请求的URI:" + requestURI;
            resp.setStatus(998); //没有权限的错误码
            new JsonResult(JsonResult.FAILURE).setMsg(errorMsg).toJson(resp);
        }
        return false;
    }
}
