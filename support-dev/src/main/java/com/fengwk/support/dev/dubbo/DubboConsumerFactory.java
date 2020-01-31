package com.fengwk.support.dev.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * @author fengwk
 */
public class DubboConsumerFactory {

	public static <T> T getService(Class<T> clazz, String url, Integer timeout) {
		ApplicationConfig application = new ApplicationConfig();
		application.setName("test");
		 
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
//		registry.setAddress("zookeeper://10.10.10.10:2181");
//		registry.setAddress("zookeeper://10.12.1.66:2181?backup=10.12.1.67:2181,10.12.1.61:2181");
//		registry.setAddress("diting://192.168.3.111:8848;diting://192.168.1.161:8848;diting://192.168.94.157:8848;zookeeper://192.168.99.51:2183?backup=192.168.99.37:2183");
		registry.setAddress("diting://192.168.3.111:8848;diting://192.168.1.161:8848;diting://192.168.94.157:8848");
		registry.setPort(28088);
		registry.setProtocol("dubbo");
//		diting://192.168.3.111:8848,192.168.1.161:8848,192.168.94.157:8848
		// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
		// 引用远程服务
		ReferenceConfig<T> reference = new ReferenceConfig<>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplication(application);
		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
		reference.setInterface(clazz);
		reference.setUrl(url);
		reference.setTimeout(timeout);
//		reference.setVersion("1.0.0");
		 
		// 和本地bean一样使用xxxService
		return reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
	}
	
}
