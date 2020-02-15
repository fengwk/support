package com.fengwk.support.spring.boot.starter.restful.auth;

/**
 * 
 * @author fengwk
 */
public class AuthorizationContext {

    static final ThreadLocal<AuthorizationInfo> THREAD_LOCAL_AUTHORIZATION = new ThreadLocal<>();
    
    static void set(AuthorizationInfo authorizationInfo) {
        THREAD_LOCAL_AUTHORIZATION.set(authorizationInfo);
    }
    
    static void clear() {
        THREAD_LOCAL_AUTHORIZATION.remove();
    }
    
    public static void checkAuthorized() {
        if (!isAuthorized()) {
            throw new AuthorizationException();
        }
    }
    
    public static boolean isAuthorized() {
        return THREAD_LOCAL_AUTHORIZATION.get() != null;
    }
    
    /**
     * 获取授权的访问令牌,使用该方法之前应先确认授权上下文是否正确授权
     * 
     * @return
     */
    public static String getAccessToken() {
        checkAuthorized();
        return THREAD_LOCAL_AUTHORIZATION.get().getAccessToken();
    }
    
    /**
     * 获取授权的用户id,使用该方法之前应先确认授权上下文是否正确授权
     * 
     * @return
     */
    public static long getUserId() {
        checkAuthorized();
        return THREAD_LOCAL_AUTHORIZATION.get().getUserId();
    }
    
}
