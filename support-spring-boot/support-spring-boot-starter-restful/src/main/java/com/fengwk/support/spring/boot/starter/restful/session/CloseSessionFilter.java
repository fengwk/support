package com.fengwk.support.spring.boot.starter.restful.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 
 * @author fengwk
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CloseSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        CustomHttpServletRequestWrapper customHttpServletRequestWrapper = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(customHttpServletRequestWrapper, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    public static class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private HttpServletRequest request;

        public CustomHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        /**
         * 创建一个可用session
         * 
         * @return
         */
        public HttpSession createSession() {
            return super.getSession(true);
        }

        public HttpServletRequest getRealRequest() {
            return request;
        }

        /**
         * 排除session
         */
        @Override
        public HttpSession getSession(boolean create) {
            return null;
        }

        @Override
        public HttpSession getSession() {
            return getSession(true);
        }

    }

}
