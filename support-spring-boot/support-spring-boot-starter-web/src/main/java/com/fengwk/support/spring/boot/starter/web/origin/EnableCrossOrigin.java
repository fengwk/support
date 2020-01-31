package com.fengwk.support.spring.boot.starter.web.origin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * spring4.2之后使用{@link CrossOrigin}注解进行跨域
 * 
 * @author fengwk
 */
@Deprecated
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableCrossOrigin {

}
