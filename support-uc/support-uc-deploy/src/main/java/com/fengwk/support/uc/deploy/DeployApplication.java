package com.fengwk.support.uc.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fengwk.support.spring.boot.starter.web.session.EnableCloseSession;

/**
 * @author fengwk
 */
@EnableCloseSession
@SpringBootApplication
public class DeployApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DeployApplication.class, args);
    }

}
