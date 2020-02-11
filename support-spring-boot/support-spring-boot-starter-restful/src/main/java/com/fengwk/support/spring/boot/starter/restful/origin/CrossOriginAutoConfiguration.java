package com.fengwk.support.spring.boot.starter.restful.origin;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author fengwk
 */
@SuppressWarnings("deprecation")
@ConditionalOnBean(annotation = EnableCrossOrigin.class)
@Configuration
public class CrossOriginAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public CrossOriginFilter crossOriginFilter() {
        return new CrossOriginFilter();
    }
    
    @ConditionalOnMissingBean
    @Bean
    public FilterRegistrationBean<CrossOriginFilter> crossOriginFilterRegistrationBean(CrossOriginFilter crossOriginFilter) {
        FilterRegistrationBean<CrossOriginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(crossOriginFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
