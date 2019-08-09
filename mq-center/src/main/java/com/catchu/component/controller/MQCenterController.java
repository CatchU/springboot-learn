package com.catchu.component.controller;

import com.alibaba.fastjson.JSONObject;
import com.catchu.common.response.Response;
import com.catchu.component.beans.MQMessageBean;
import com.catchu.component.service.RocketMqService;
import com.catchu.exception.beans.BadRequestException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送CONTROLLER
 */
@RestController
@RequestMapping("/")
@Slf4j
public class MQCenterController {

    @Autowired
    private RocketMqService rocketMqService;

    /**
     * 发送ROCKETMQ
     *
     * @param bean
     * @return
     */
    @PostMapping("/message/rocketmq/1.0.0")
    public Response sendMessageForRocketMQ(@RequestBody MQMessageBean bean){
        log.info("uuid:{},desc:{},params:{}",bean.getBusinessId(),"发送ROCKETMQ消息[CONTROLLER]", JSONObject.toJSONString(bean));
        if(Strings.isNullOrEmpty(bean.getTopic())){
            log.info("uuid:{},desc:{},result:{}",bean.getBusinessId(),"发送ROCKETMQ消息[CONTROLLER]", "invalid topic info");
            return null;
        }
        if(Strings.isNullOrEmpty(bean.getBusinessTag())){
            log.info("uuid:{},desc:{},result:{}",bean.getBusinessId(),"发送ROCKETMQ消息[CONTROLLER]", "invalid businessTag info");
            return null;
        }
        if(Strings.isNullOrEmpty(bean.getMessage())){
            log.info("uuid:{},desc:{},result:{}",bean.getBusinessId(),"发送ROCKETMQ消息[CONTROLLER]", "invalid message info");
            return null;
        }
        try {
            return new Response<>().ok(rocketMqService.sendMessage(bean));
        }catch (Exception e){
            log.info("uuid:{},desc:{},error:{}",bean.getBusinessId(),"发送ROCKETMQ消息[CONTROLLER]-失败", e.getMessage(),e);
            return new Response<>().error("发送失败");
        }
    }
}
