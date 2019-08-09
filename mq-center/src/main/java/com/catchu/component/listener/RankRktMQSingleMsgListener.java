package com.catchu.component.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author liang
 * @description
 * @date 2019/3/25 15:32
 */
@Slf4j
@Component
public class RankRktMQSingleMsgListener implements MessageListenerConcurrently {


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String uuid= UUID.randomUUID().toString().replaceAll("-","");

        log.info("uuid:{},desc:{},params:{}",
                uuid,"RocketMQ消费消息",JSONObject.toJSONString(list));
        try {
            for(MessageExt message : list){
                byte[] body = message.getBody();
                log.info("desc:{},param:{}","消息体",new String(body,"UTF-8"));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("uuid:{},desc:{},message:{}",uuid,"RocketMQ消费消息-异常",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

}
