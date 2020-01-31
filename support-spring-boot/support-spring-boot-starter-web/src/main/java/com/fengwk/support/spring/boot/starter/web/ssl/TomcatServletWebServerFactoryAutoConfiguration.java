package com.fengwk.support.spring.boot.starter.web.ssl;
//package com.fengwk.support.spring.boot.starter.https;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@ConditionalOnBean(annotation = EnableTomcatHttps.class)
//@Configuration
//public abstract class TomcatServletWebServerFactoryConfig {
//
//    private static final String SCHEME_HTTP = "http";
//
//    private Boolean enableRedirectHttps = false;
//    private Integer port = 80;
//    private Integer httpPort = 443;
//
//    @Bean
//    protected TomcatServletWebServerFactory tomcatServletWebServerFactory() {
//        TomcatServletWebServerFactory tomcat = null;
//        if (enableRedirectHttps) {
//            tomcat = new TomcatServletWebServerFactory() {
//                @Override
//                protected void postProcessContext(Context context) {
//                    SecurityConstraint securityConstraint = new SecurityConstraint();
//                    securityConstraint.setUserConstraint("CONFIDENTIAL");
//                    SecurityCollection collection = new SecurityCollection();
//                    // http https 共存
//                    // collection.addMethod("post");
//                    collection.addPattern("/*");
//                    securityConstraint.addCollection(collection);
//                    context.addConstraint(securityConstraint);
//                }
//            };
//            tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
//        } else {
//            tomcat = new TomcatServletWebServerFactory();
//        }
//        return tomcat;
//    }
//
//    private Connector initiateHttpConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme(SCHEME_HTTP);
//        connector.setPort(httpPort);
//        connector.setSecure(false);
//        connector.setRedirectPort(port);
//        return connector;
//    }
//
//    public Boolean getEnableRedirectHttps() {
//        return enableRedirectHttps;
//    }
//
//    public void setEnableRedirectHttps(Boolean enableRedirectHttps) {
//        this.enableRedirectHttps = enableRedirectHttps;
//    }
//
//    public Integer getPort() {
//        return port;
//    }
//
//    public void setPort(Integer port) {
//        this.port = port;
//    }
//
//    public Integer getHttpPort() {
//        return httpPort;
//    }
//
//    public void setHttpPort(Integer httpPort) {
//        this.httpPort = httpPort;
//    }
//
//}