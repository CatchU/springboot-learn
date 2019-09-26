package com.catchu.http;

import com.alibaba.fastjson.JSONObject;
import com.catchu.http.beans.HttpClientBean;
import com.catchu.http.builders.HttpParamsBuilder;
import com.catchu.http.enums.ContentTypeEnum;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.catchu.http.beans.HttpClientBean.DEFAULT_ENCODING;

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

    /**
     * 执行Post请求
     * @param url
     * @param params
     * @param encode
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String,String> params, String encode, ContentTypeEnum contentTypeEnum) throws Exception{
        HttpClientBean clientBean = new HttpClientBean(url, params, encode,contentTypeEnum);
        return clientBean.doPost();
    }

    /**
     * 执行Post请求,参数Object类型
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String,Object> params) throws Exception{
        if(Objects.isNull(params) || params.size()<1){
            HttpClientBean clientBean = new HttpClientBean(url, Collections.EMPTY_MAP, DEFAULT_ENCODING,ContentTypeEnum.APPOLICATION_JSON);
            return clientBean.doPost();
        }
        Map<String, String> p = HttpParamsBuilder.filterBlank(params);
        HttpClientBean clientBean = new HttpClientBean(url, p, DEFAULT_ENCODING,ContentTypeEnum.APPOLICATION_JSON);
        return clientBean.doPost();
    }

    /**
     * 执行Post请求,参数是单纯的List<?>或Set<?>
     * @param url
     * @param requestBody
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Collection<?> requestBody) throws Exception{
        if(Objects.isNull(requestBody) || CollectionUtils.isEmpty(requestBody)){
            HttpClientBean clientBean = new HttpClientBean(url, Collections.EMPTY_MAP, DEFAULT_ENCODING,ContentTypeEnum.APPOLICATION_JSON);
            return clientBean.doPost();
        }

        if(requestBody instanceof List){
            List list = (List)requestBody;
            Map<String,String> map = new HashMap<>(1);
            map.put(HttpParamsBuilder.REQUEST_BODY_NON_KEY, JSONObject.toJSONString(list));
            HttpClientBean clientBean = new HttpClientBean(url, map, DEFAULT_ENCODING, ContentTypeEnum.APPOLICATION_JSON);
            return clientBean.doPost();
        }

        if(requestBody instanceof Set){
            Set set = (Set)requestBody;
            Map<String,String> map = new HashMap<>(1);
            map.put(HttpParamsBuilder.REQUEST_BODY_NON_KEY,JSONObject.toJSONString(set));
            HttpClientBean clientBean = new HttpClientBean(url, map, DEFAULT_ENCODING, ContentTypeEnum.APPOLICATION_JSON);
            return clientBean.doPost();
        }

        throw new Exception("invalid request body");
    }
}
