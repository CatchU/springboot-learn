package com.catchu.http.builders;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 参数构造器
 * @author junzhongliu
 * @date 2019/9/25 19:57
 */
public class HttpParamsBuilder {

    /**
     * 问号标识符
     */
    public static final String QUESTION_MARK = "?";

    public static final String REQUEST_BODY_NON_KEY = "REQUEST_BODY_NON_KEY";

    /**
     * 构造Get请求参数
     * @param url
     * @param params
     * @param encode
     * @return
     */
    public static String getParamBuilder(String url, Map<String,String> params, String encode){
        if(params !=null && !params.isEmpty()){
            List<NameValuePair> nvps = new ArrayList<>();
            if(!url.contains(QUESTION_MARK) || !url.endsWith(QUESTION_MARK)){
                url += QUESTION_MARK;
            }
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for(Map.Entry<String,String> entry : entries){
                nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            url += URLEncodedUtils.format(nvps,encode);
        }
        return url;
    }

    /**
     * 构造POST请求的参数
     * @param params
     * @return
     */
    public static List<NameValuePair> formParamsBuilder(Map<String,?> params){
        return buildRequestBody(params);
    }

    public static List<NameValuePair> buildRequestBody(Map<String,?> params){
        return params.entrySet().parallelStream()
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .map(entry -> new BasicNameValuePair(entry.getKey(),entry.getValue().toString()))
                .collect(Collectors.toList());
    }

    /**
     * POST请求参数构造
     */
    public static String jsonParamsBuilder(Map<String, String> params) {
        if (Objects.nonNull(params) && !params.isEmpty()) {
            return JSONObject.toJSONString(params);
        }
        return null;
    }

    /**
     * MAP构建
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> mapParamsBuilder(Object obj) {
        Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(obj), Map.class);
        return filterBlank(map);
    }

    /**
     * 字符串转OBJECT
     *
     * @param params
     * @param clazz
     * @return
     */
    public static <T> T objectParamsBuilder(String params, Class<T> clazz) {
        return JSONObject.parseObject(params, clazz);
    }

    /**
     * 字符串转LIST
     *
     * @param params
     * @param clazz
     * @return
     */
    public static <T> List<T> arrayParamsBuilder(String params, Class<T> clazz) {
        return JSONObject.parseArray(params, clazz);
    }

    /**
     * 过滤掉空的key和value
     * @param params
     * @return
     */
    public static Map<String,String> filterBlank(Map<String,?> params){
        return params.entrySet().parallelStream()
                .filter(entry -> Objects.nonNull(entry.getKey()) && StringUtils.isNotBlank(entry.getKey()))
                .filter(entry -> Objects.nonNull(entry.getValue()) && StringUtils.isNotBlank(entry.getValue().toString()))
                .collect(Collectors.toMap(entry -> entry.getKey(),entry -> entry.getValue().toString()));
    }
}
