package com.catchu.component.beans;

import lombok.Data;
import com.google.common.base.Strings;

import java.util.UUID;

@Data
public class MQMessageBean {

    /**
     * 业务ID
     */
    private String businessId;


    /**
     * 发送主题
     */
    private String topic;

    /**
     * 业务分类
     */
    private String businessTag;

    /**
     * 消息体-JSON格式
     */
    private String message;
    /**
     * Message type：Timing Order Delay
     */
    private String type;
    /**
     * 延时消息时间（延时的时间）Message type = Delay时设置
     */
    private long delayTime;

    /**
     * 定时消费消息时间（日期yyyy-MM-dd HH:mm:ss）Message type = Timing时设置
     */
    private String startDeliveryTime;

    public String getBusinessId(){
        if(Strings.isNullOrEmpty(businessId)){
            this.businessId=UUID.randomUUID().toString();
        }
        return businessId;
    }
}
