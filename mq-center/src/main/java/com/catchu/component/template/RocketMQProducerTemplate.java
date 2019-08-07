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
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Data
public class RocketMQProducerTemplate {

    @Autowired
    private RocketMQConfig rocketMQConfig;

    private DefaultMQProducer producer = new
            DefaultMQProducer("please_rename_unique_group_name");

    @PostConstruct
    public void initProducer() {
        log.info("desc:{}", "rocketmq producer starting");
        Properties pro = new Properties();
//        pro.put(PropertyKeyConst.AccessKey, rocketMQConfig.getAccessKey());
//        pro.put(PropertyKeyConst.SecretKey, rocketMQConfig.getSecretKey());
//        pro.put(PropertyKeyConst.ONSAddr, rocketMQConfig.getOnsaddr());
//        pro.put(PropertyKeyConst.GROUP_ID, rocketMQConfig.getGroupId());
//        producer = ONSFactory.createProducer(pro);
//        producer.start();
        log.info("desc:{}", "rocketmq producer started");
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
        return sendMessage(topic, tag, key, null, null, body);
    }

    public Boolean sendMessage(String topic, String tag, String key, Long time, TimeUnit timeUnit, byte[] body) {
        Message msg = new Message(topic, tag, body);
        try {
            if (!Strings.isNullOrEmpty(key)) {
                msg.setKeys(key);
            }
            if (!Objects.isNull(time)) {
                switch (timeUnit) {
                    case SECONDS:
                        break;
                    case MINUTES:
                        time = time * 60;
                        break;
                    case HOURS:
                        time = time * 60 * 60;
                        break;
                    case DAYS:
                        time = time * 24 * 60 * 60;
                        break;
                    default:
                        break;
                }
                msg.setDelayTimeLevel(time);
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
