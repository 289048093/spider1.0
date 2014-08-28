package com.ejushang.spider.web.common;


import com.ejushang.spider.domain.User;
import com.ejushang.spider.util.SessionUtils;
import com.ejushang.spider.web.util.IpUtil;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * log4j MDC Filter
 *
 * @author Athens(刘杰)
 */
public class Log4jMDCFilter implements Filter {

    private static final String REQUEST_URL = "requestURIWithString";
    private static final String USER_INFO = "userInfo";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 记录用户的请求地址
        MDC.put(REQUEST_URL, httpServletRequest.getRequestURI());
        // 记录用户信息及 ip
        User user = SessionUtils.getUser();
        String username = "未登录";
        if(user != null) {
            username = user.getUsername();
        }
        String logInfo = new StringBuilder().append("#").append(username).append("#").append(", ip:").append(IpUtil.getIpAddress(httpServletRequest)).toString();
        MDC.put(USER_INFO, logInfo);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.remove(REQUEST_URL);
        MDC.remove(USER_INFO);
    }
}
