package com.catchu.http;

import com.catchu.http.beans.HttpClientBean;

import java.util.Map;

/**
 * HttpClient 客户端调用工具
 * @author junzhongliu
 * @date 2019/9/25 20:38
 */
public class HttpClientAssistant {

    /**
     * 执行Get请求
     * @param url
     * @param params
     * @param encode
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String,String> params, String encode) throws Exception{
        HttpClientBean clientBean = new HttpClientBean(url, params, encode);
        return clientBean.doGet();
    }
}
