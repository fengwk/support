package com.fengwk.support.spring.boot.starter.restful.origin;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 
 * @author fengwk
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class CrossOriginFilter implements Filter {

	public CrossOriginFilter() {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestOrigin = httpRequest.getHeader("Origin");
        String requestMethod = httpRequest.getHeader("Access-Control-Request-Method");
        Enumeration<String> requestHeaders = httpRequest.getHeaders("Access-Control-Request-Headers");
        if (isAllow(requestOrigin, requestMethod, requestHeaders)) {
        	httpResponse.setHeader("Access-Control-Allow-Origin", requestOrigin);
        	httpResponse.setHeader("Access-Control-Allow-Methods", requestMethod);
        	while (requestHeaders.hasMoreElements()) {
        		httpResponse.setHeader("Access-Control-Allow-Headers", requestHeaders.nextElement());
        	}
        	httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
        chain.doFilter(request, response);
	}
	
	protected boolean isAllow(String requestOrigin, String requestMethod, Enumeration<String> requestHeaders) {
        return true;
    }
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {}
	
}
