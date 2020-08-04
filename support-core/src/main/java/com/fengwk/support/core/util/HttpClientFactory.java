package com.fengwk.support.core.util;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * 
 * @author fengwk
 */
public final class HttpClientFactory {

    private static volatile CloseableHttpClient INSTANCE;

    public static CloseableHttpClient getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpClientFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = newInstance();
                }
            }
        }
        return INSTANCE;
    }
    
    private static CloseableHttpClient newInstance() {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolingHttpClientConnectionManager.setMaxTotal(80);// 设置最大连接数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(30);// 默认的每个路由的最大连接数
        poolingHttpClientConnectionManager.setValidateAfterInactivity(3000);// 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间,如果超过,释放socket重新建立
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(30000).build();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).build();
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultSocketConfig(socketConfig)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

}
