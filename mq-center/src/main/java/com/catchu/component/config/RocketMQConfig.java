package com.catchu.component.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * RocketMq配置
 */
@Configuration
@Component
@Data
public class RocketMQConfig {
    // nameserver 地址
    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    // 发送超时时间
    @Value("${rocketmq.timeOut}")
    private Integer timeOut;

    // 生产者组
    @Value("${rocketmq.producerGroup}")
    private String producerGroup;

    // 消费者订阅的topics
    @Value("${rocketmq.consumerTopics}")
    private String consumerTopics;
}
