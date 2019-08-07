package com.catchu.component.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class RocketMQConfig {
    @Value("${aliyun.rocketmq.ONSAddr}")
    private String onsaddr;
    @Value("${aliyun.rocketmq.AccessKey}")
    private String accessKey;
    @Value("${aliyun.rocketmq.SecretKey}")
    private String secretKey;
    @Value("${aliyun.rocketmq.GroupId}")
    private String groupId;
}
