package com.fengwk.support.spring.boot.starter.restful.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author fengwk
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Autowired
    volatile Authenticator authenticator;

    AuthorizationInterceptor() {}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String accessToken = request.getHeader(HEADER_AUTHORIZATION);
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        if (StringUtils.isBlank(accessToken)) {
            // 非认证请求
            return true;
        }

        AuthorizationInfo authorizationInfo = authenticator.authorize(accessToken);
        if (authorizationInfo == null) {// 认证失败
            response.setStatus(HttpStatus.UNAUTHORIZED.value());// 401
            return false;
        }

        AuthorizationContext.set(authorizationInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        AuthorizationContext.clear();
    }

}
