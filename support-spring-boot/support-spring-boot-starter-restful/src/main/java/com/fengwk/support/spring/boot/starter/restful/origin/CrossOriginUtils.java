package com.fengwk.support.spring.boot.starter.restful.origin;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author fengwk
 */
public class CrossOriginUtils {

    private CrossOriginUtils() {}
    
    public static void crossOrigin(HttpServletRequest request, HttpServletResponse response) {
        String requestOrigin = request.getHeader("Origin");
        String requestMethod = request.getHeader("Access-Control-Request-Method");
        Enumeration<String> requestHeaders = request.getHeaders("Access-Control-Request-Headers");
        if (isAllow(requestOrigin, requestMethod, requestHeaders)) {
            response.setHeader("Access-Control-Allow-Origin", requestOrigin);
            response.setHeader("Access-Control-Allow-Methods", requestMethod);
            while (requestHeaders.hasMoreElements()) {
                response.setHeader("Access-Control-Allow-Headers", requestHeaders.nextElement());
            }
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
    }
    
    protected static boolean isAllow(String requestOrigin, String requestMethod, Enumeration<String> requestHeaders) {
        return true;
    }
    
}
