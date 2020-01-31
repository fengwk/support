package com.fengwk.support.spring.boot.starter;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author fengwk
 */
@ConfigurationProperties(prefix = "spring.profiles")
public class ContextUtils implements ApplicationContextAware {

    private static volatile String active;
    
    private static volatile ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtils.applicationContext = applicationContext;
    }

    public void setActive(String active) {
        ContextUtils.active = active;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) {
        if (applicationContext == null) {
            return null;
        }
        try {
            return (T) applicationContext.getBean(clazz);
        } catch (BeansException e) {
            return null;
        }
    }
    
    public static boolean isDev() {
        return "dev".equalsIgnoreCase(active);
    }

}
