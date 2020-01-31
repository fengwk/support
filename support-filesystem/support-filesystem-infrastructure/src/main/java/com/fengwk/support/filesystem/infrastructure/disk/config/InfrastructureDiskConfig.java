package com.fengwk.support.filesystem.infrastructure.disk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author fengwk
 */
@ComponentScan("com.fengwk.support.filesystem.infrastructure.disk")
@Configuration
public class InfrastructureDiskConfig implements WebMvcConfigurer {
    
    @Value("${support.filesystem.disk.root}")
    volatile String root;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(regularize(root));
    }
    
    private String regularize(String root) {
        if (!root.startsWith("file:")) {
            root = "file:" + root;
        }
        // 截取url时会忽略前缀/,使用不以/结尾的root拼接URL拼接时会忽略一个层级
        // org.springframework.web.servlet.resource.PathResourceResolver.getResource(String, HttpServletRequest, List<? extends Resource>)
        if (!root.endsWith("/")) {
            root += "/";
        }
        return root;
    }
    
}
