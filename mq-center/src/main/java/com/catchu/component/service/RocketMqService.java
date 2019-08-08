package com.catchu.component.service;

import com.alibaba.fastjson.JSONObject;
import com.catchu.component.beans.MQMessageBean;
import com.catchu.component.template.RocketMQProducerTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class RocketMqService {

    @Autowired
    private RocketMQProducerTemplate rocketMQProducerTemplate;

    /**
     * 发送ROCKETMQ消息
     *
     * @param bean
     */
    public Boolean sendMessage(MQMessageBean bean) throws Exception {
        log.info("uuid:{},desc:{},params:{}",bean.getBusinessId(),"发送ROCKETMQ消息[SERVICE]", JSONObject.toJSONString(bean));
        Boolean b =  rocketMQProducerTemplate.sendMessage(generateMessage(bean));
        log.info("uuid:{},desc:{},result:{}",bean.getBusinessId(),"发送ROCKETMQ消息[SERVICE]",b);
        return b;
    }
    /**
     * 生成消息体
     * @param mqMessageBean
     * @return
     */
    private Message generateMessage(MQMessageBean mqMessageBean) throws Exception {
        Message msg = new Message();
        msg.setTags(mqMessageBean.getBusinessTag());
        msg.setTopic(mqMessageBean.getTopic());
        msg.setBody(mqMessageBean.getMessage().getBytes());
        if(Objects.nonNull(mqMessageBean.getDelayLevel())){
            msg.setDelayTimeLevel(mqMessageBean.getDelayLevel());
        }
        return msg;
    }
}
