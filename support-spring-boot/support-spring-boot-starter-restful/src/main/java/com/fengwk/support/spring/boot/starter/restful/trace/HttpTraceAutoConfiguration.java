package com.fengwk.support.spring.boot.starter.restful.trace;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fengwk.support.core.gson.GsonUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Aspect
@Configuration
public class HttpTraceAutoConfiguration {
	
	@Autowired
	private HttpServletRequest request;
	
    @Around("@annotation(com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace) || @within(com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Long beginTime = null;
		String method = null;
		String url = null;
		String headers = null;
		String params = null;
		if (log.isInfoEnabled()) {
		    beginTime = System.currentTimeMillis();
            method = request.getMethod();
            url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
            headers = getHeaders();
            params = GsonUtils.toJson(point.getArgs());
        }
		Object result = null;
		try {
			result = point.proceed();
			return result;
		} finally {
			if (log.isInfoEnabled()) {
				log.info(new TraceRecord((
				        System.currentTimeMillis() - beginTime) + "ms", 
				        method, 
				        url, 
				        headers, 
				        params, 
				        GsonUtils.toJson(result)).toString());
			}
		}
	}
	
	private String getHeaders() {
	    Map<String, List<String>> headers = new HashMap<>();
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String headerName = headerNames.nextElement();
	        List<String> valuesTemp = headers.computeIfAbsent(headerName, x -> new ArrayList<>());
	        Enumeration<String> headerValues = request.getHeaders(headerName);
	        while (headerValues.hasMoreElements()) {
	            valuesTemp.add(headerValues.nextElement());
	        }
	    }
	    return GsonUtils.toJson(headers);
	}
	
	@Getter
	private final static class TraceRecord {
		
		private final String execute;
    	private final String method;
        private final String url;
        private final String headers;
        private final String params;
        private final String result;

        private TraceRecord(String execute, String method, String url, String headers, String params, String result) {
            this.execute = execute;
            this.method = method;
            this.url = url;
            this.headers = headers;
            this.params = params;
            this.result = result;
        }

        @Override
        public String toString() {
            return "TraceRecord [execute=" + execute + ", method=" + method + ", url=" + url + ", headers=" + headers
                    + ", params=" + params + ", result=" + result + "]";
        }

	}

}
