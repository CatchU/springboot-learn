package com.catchu.http.beans;

import com.catchu.http.builders.HttpClientPoolManagerBuilder;
import com.catchu.http.builders.HttpParamsBuilder;
import com.catchu.http.enums.ContentTypeEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * HttpClientBean
 * @author junzhongliu
 * @date 2019/9/25 19:35
 */
public class HttpClientBean {

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求参数
     */
    private Map<String,String> params;

    /**
     * 编码方式
     */
    private String encode = DEFAULT_ENCODING;

    /**
     * content-type
     */
    private ContentTypeEnum contentType = ContentTypeEnum.APPOLICATION_X_WWW_FORM_URLENCODED;

    /**
     * 发Get请求
     * @return
     * @throws Exception
     */
    public String doGet() throws Exception {
        CloseableHttpResponse response = null;
        String result;
        CloseableHttpClient httpClient = HttpClientPoolManagerBuilder.build().buildHttpClient(DEFAULT_REQUEST_CONFIG);
        url = HttpParamsBuilder.getParamBuilder(url, params, encode);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(DEFAULT_REQUEST_CONFIG);
        try {
            response = httpClient.execute(httpGet);
            assetSuccess(response);
            result = EntityUtils.toString(response.getEntity(), encode);
        } finally {
            release(response);
        }
        return result;
    }

    /**
     * 发Post请求
     * @return
     * @throws Exception
     */
    public String doPost() throws Exception{
        String result="";
        CloseableHttpResponse response=null;
        try {
            CloseableHttpClient httpClient = HttpClientPoolManagerBuilder.build().buildHttpClient(DEFAULT_REQUEST_CONFIG);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(DEFAULT_REQUEST_CONFIG);
            httpPost.setHeader("Content-Type",contentType.getContentType());
            httpPost.setEntity(contentType.buildRequestEntity(params,encode));
            response = httpClient.execute(httpPost);
            assetSuccess(response);
            HttpEntity entity = response.getEntity();
            if(Objects.nonNull(entity)){
                result = EntityUtils.toString(entity, encode);
                EntityUtils.consume(entity);
            }
        } finally {
            release(response);
        }
        return result;
    }

    private static void assetSuccess(CloseableHttpResponse response) throws Exception {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return;
        }
        throw new Exception("error statusCode:" + response.getStatusLine().getStatusCode() + "");
    }

    private static final RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(1500)
            .setConnectionRequestTimeout(1500)
            .setSocketTimeout(1500)
            .setRedirectsEnabled(true)
            .build();

    /**
     * 资源回收
     * @param response
     */
    private void release(CloseableHttpResponse response){
        try {
            if(response!=null){
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******构造函数******/
    public HttpClientBean(String url) {
        this(url,null,null,null);
    }

    public HttpClientBean(String url, Map<String, String> params) {
        this(url,params,null,null);
    }

    public HttpClientBean(String url, Map<String, String> params, String encode) {
        this(url,params,encode,null);
    }

    public HttpClientBean(String url, Map<String, String> params, String encode, ContentTypeEnum contentType) {
        this.url = url;
        this.params = params;
        if(null != encode && !"".equals(encode)){
            this.encode = encode;
        }
        if(null != contentType){
            this.contentType = contentType;
        }
    }
}
