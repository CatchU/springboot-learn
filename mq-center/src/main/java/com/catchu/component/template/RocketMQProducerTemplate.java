package com.catchu.component.template;

import com.catchu.component.config.RocketMQConfig;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Component
@Slf4j
@Data
public class RocketMQProducerTemplate {

    @Autowired
    private RocketMQConfig rocketMQConfig;

    private DefaultMQProducer producer;

    @PostConstruct
    public void initProducer() throws Exception{
        log.info("desc:{}", "rocketmq producer starting");
        if (this.producer == null) {
            this.producer = new DefaultMQProducer();
        }
        this.producer.setProducerGroup(this.rocketMQConfig.getProducerGroup());
        this.producer.setSendMsgTimeout(this.rocketMQConfig.getTimeOut());
        this.producer.setNamesrvAddr(this.rocketMQConfig.getNamesrvAddr());
        // 失败重试三次
        this.producer.setRetryTimesWhenSendFailed(3);
        this.producer.start();
        log.info("desc:{}", "rocketmq producer started");
        Message message = new Message();
        message.setBody("fdsaf".getBytes());
        message.setTags("fdsja");
        message.setTopic("fdsalf");
        this.sendMessage(message);
    }

    @PreDestroy
    public void destoryProducer(){
        producer.shutdown();
    }

    /**
     * 发送消息
     *
     * @param topic
     * @param tag
     * @param body
     */
    public Boolean sendMessage(String topic, String tag, String body) {
        return sendMessage(topic, tag, null, body.getBytes());
    }

    /**
     * 发送消息
     *
     * @param tag
     *            你要发送消息的tag
     * @param key
     * @param body
     */
    public Boolean sendMessage(String topic, String tag, String key, byte[] body) {
        return sendMessage(topic, tag, key, null, body);
    }

    public Boolean sendMessage(String topic, String tag, String key, Integer delayLevel, byte[] body) {
        Message msg = new Message(topic, tag, body);
        try {
            if (!Strings.isNullOrEmpty(key)) {
                msg.setKeys(key);
            }
            if(Objects.nonNull(delayLevel)){
                msg.setDelayTimeLevel(delayLevel);
            }
            SendResult sendResult = producer.send(msg);
            if (sendResult != null) {
                log.info("消息发送成功：{}", sendResult.toString());
                return true;
            }
        } catch (Exception e) {
            log.info("消息发送失败：{}", e);
        }
        return false;
    }

    /**
     * 发送消息
     * @param message
     */
    public Boolean sendMessage(Message message) {
        try{
            SendResult sendResult = producer.send(message);
            if (sendResult != null) {
                log.info("消息发送成功：{}", sendResult.toString());
                return true;
            }
        } catch (Exception e) {
            log.info("消息发送失败：{}", e);
        }
        return false;
    }
}
