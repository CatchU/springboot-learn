package com.catchu.component.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class RocketMQConfig {
    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.timeOut}")
    private Integer timeOut;
    @Value("${rocketmq.producerGroup}")
    private String producerGroup;
    @Value("${rocketmq.consumerTopics}")
    private String consumerTopics;
}
