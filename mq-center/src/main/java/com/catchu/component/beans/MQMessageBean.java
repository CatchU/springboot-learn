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
     * 延时消息的级别 Message type = Delay时设置
     * @see RocketMQDelayEnum
     */
    private Integer delayLevel;

    public String getBusinessId(){
        if(Strings.isNullOrEmpty(businessId)){
            this.businessId=UUID.randomUUID().toString();
        }
        return businessId;
    }
}
