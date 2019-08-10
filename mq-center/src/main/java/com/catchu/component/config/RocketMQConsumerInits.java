package com.catchu.component.config;

import com.alibaba.fastjson.JSONObject;
import com.catchu.component.listener.RocketMQMsgListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

/**
 * RocketMQ消费者初始化，实际中用在消息消费侧
 * 与消息监听器搭配使用，{@link RocketMQMsgListener}
 * @author junzhongliu
 * @date 2019/8/9 12:50
 */
@Component
@Slf4j
public class RocketMQConsumerInits {
    @Autowired
    private RocketMQConfig rocketMQConfig;
    @Autowired
    private RocketMQMsgListener rocketMQMsgListener;
    private DefaultMQPushConsumer consumer;
    @Autowired
    private Environment environment;
    private static String[] demoEnvironment = {"load","demo","dev"};


    @PostConstruct
    public void initConsumer() throws Exception{
        log.info("desc:{}", "rocketmq consumer starting");
        consumer = new DefaultMQPushConsumer("MagicMQ-Consumer");
        consumer.setNamesrvAddr(this.rocketMQConfig.getNamesrvAddr());
        Arrays.asList(demoEnvironment).forEach(demo->{
            if(Arrays.asList(environment.getActiveProfiles()).contains(demo)){
                // 测试环境设置广播消费
                consumer.setMessageModel(MessageModel.BROADCASTING);
            }else{
                // 设置集群消费
                consumer.setMessageModel(MessageModel.CLUSTERING);
            }
        });

        log.info("desc:{},当前环境:{},设置的消息模式是:{}",
                "消费者消费模式", JSONObject.toJSONString(environment.getActiveProfiles()),consumer.getMessageModel());
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 批量消费,每次拉取10条
        consumer.setConsumeMessageBatchMaxSize(10);
        consumer.subscribe(rocketMQConfig.getConsumerTopics(),  "*");
        consumer.registerMessageListener(rocketMQMsgListener);
        consumer.start();
        log.info("desc:{}", "rocketmq consumer started");
    }

    @PreDestroy
    public void destroyConsumer() {
        consumer.shutdown();
    }
}
