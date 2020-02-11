package com.fengwk.support.spring.boot.starter.restful.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author fengwk
 */
@Configuration
public class CloseSessionAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public CloseSessionFilter closeSessionFilter() {
        return new CloseSessionFilter();
    }

    @ConditionalOnMissingBean
    @Bean
    public FilterRegistrationBean<CloseSessionFilter> closeSessionFilterRegistrationBean(CloseSessionFilter closeSessionFilter) {
        FilterRegistrationBean<CloseSessionFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(closeSessionFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
    
}
