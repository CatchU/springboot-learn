package com.catchu.http.enums;

import com.catchu.http.builders.HttpParamsBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public enum ContentTypeEnum {

    APPOLICATION_XHTML_XML("application/xhtml+xml"),
    APPOLICATION_XML("application/xml"),
    APPOLICATION_ATOM_XML("application/atom+xml"),
    APPOLICATION_JSON("application/json") {
        @Override
        public HttpEntity buildRequestEntity(Map<String, String> params, String encoding) {
            //如果包含这个key说明body是个list，直接按json字符串读取即可。
            if (params.keySet().contains(HttpParamsBuilder.REQUEST_BODY_NON_KEY)) {
                return new StringEntity(params.get(HttpParamsBuilder.REQUEST_BODY_NON_KEY), encoding);
            }
            return new StringEntity(HttpParamsBuilder.jsonParamsBuilder(params), encoding);
        }
    },
    APPOLICATION_PDF("application/pdf"),
    APPOLICATION_MSWORD("application/msword"),
    APPOLICATION_OCTET_STREAM("application/octet-stream"),
    APPOLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded") {
        @Override
        public HttpEntity buildRequestEntity(Map<String, String> params, String encoding) throws UnsupportedEncodingException {
            return new UrlEncodedFormEntity(HttpParamsBuilder.formParamsBuilder(params), encoding);
        }
    },
    APPOLICATION_MULTIPART_FORM_DATA("multipart/form-data");

    ContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }

    private String contentType;

    public String getContentType() {
        return this.contentType;
    }

    public HttpEntity buildRequestEntity(Map<String, String> params, String encoding) throws UnsupportedEncodingException {
        return new UrlEncodedFormEntity(HttpParamsBuilder.formParamsBuilder(params), encoding);
    }
}
