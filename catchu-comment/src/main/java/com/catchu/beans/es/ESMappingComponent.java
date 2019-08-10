package com.catchu.beans.es;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * ES MAPPING组件
 *
 */
public class ESMappingComponent {

    private ESMappingComponent(){}

    private static class Assistant{
        final static ESMappingComponent instance=new ESMappingComponent();
    }

    public static ESMappingComponent getInstance(){
        return Assistant.instance;
    }

    public XContentBuilder getXContentBuilder(){
        try {
            XContentBuilder builder= XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("resourceId");
                    {
                        builder.field("type", "long");
                    }
                    builder.endObject();
                    builder.startObject("resourceType");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("userId");
                    {
                        builder.field("type", "long");
                    }
                    builder.endObject();
                    builder.startObject("userType");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("verifyStatus");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("verifyChannel");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("recordStatus");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("contentOriginal");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();
                    builder.startObject("contentShow");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();
                    builder.startObject("contentType");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("createTime");
                    {
                        builder.field("type", "date");
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
