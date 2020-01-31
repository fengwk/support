package com.fengwk.support.spring.boot.starter.web.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fengwk.support.spring.boot.starter.ContextUtils;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author fengwk
 */
@EnableSwagger2
@Configuration
public class SwaggerAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().build())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(ContextUtils.isDev() ? PathSelectors.any() : PathSelectors.none())
                .build();
    }
    
}
