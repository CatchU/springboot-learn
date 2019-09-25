package com.catchu.http.builders;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.*;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * httpclient连接池管理构造器
 * @author junzhongliu
 * @date 2019/9/25 17:47
 */
public class HttpClientPoolManagerBuilder {

    private PoolingHttpClientConnectionManager connManager = null;

    private HttpClientPoolManagerBuilder(){
        try {
            SSLContext sslContext = createIgnoreVerifySSL();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(2000);
            // 设置每个连接的路由数
            connManager.setDefaultMaxPerRoute(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Assistant{
        final static HttpClientPoolManagerBuilder BUILDER = new HttpClientPoolManagerBuilder();
    }

    public static HttpClientPoolManagerBuilder build(){
        return Assistant.BUILDER;
    }

    /**
     * 创建自定义的HttpClient对象
     * @param requestConfig 请求超时配置
     * @param retryHandler 失败重试策略
     * @return
     */
    public CloseableHttpClient buildHttpClient(RequestConfig requestConfig, HttpRequestRetryHandler retryHandler){
        return HttpClients.custom()
                // 把请求相关的超时信息配置在调用端
                .setDefaultRequestConfig(requestConfig)
                // 把重试策略配置在调用端
                .setRetryHandler(retryHandler)
                // p配置连接池管理对象
                .setConnectionManager(connManager)
                .build();
    }

    /**
     * 获取httpclient重试策略
     */
    public static HttpRequestRetryHandler buildHttpClientRetryHandler() {
        int maxExecCount = 3;
        return (exception, executionCount, context) -> {
            if (executionCount >= maxExecCount) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                return true;
            }
            if (exception instanceof UnknownHostException) {
                return false;
            }
            if (exception instanceof SSLException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        };
    }

    /**
     * 创建忽略SSL的SSLContext对象,用于绕过https验证，否则httpclient调用https会失败
     * @return
     */
    private SSLContext createIgnoreVerifySSL() throws Exception{
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null,new TrustManager[]{trustManager},null);
        return sslContext;
    }
}
