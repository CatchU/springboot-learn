package com.catchu.component.service;

import com.alibaba.fastjson.JSONObject;
import com.catchu.component.beans.MQMessageBean;
import com.catchu.component.config.RocketMQServiceConstant;
import com.catchu.component.template.RocketMQProducerTemplate;
import com.catchu.component.util.RocketMQServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.apache.rocketmq.common.message.Message;

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

        // make sure message type is valid
        if (StringUtils.isEmpty(mqMessageBean.getType())) {
            mqMessageBean.setType(RocketMQServiceConstant.ORDER_MESSAGE);
        }

        switch (mqMessageBean.getType()) {
            // Generate Delay message
            case RocketMQServiceConstant.DELAY_MESSAGE:
                msg.setStartDeliverTime(System.currentTimeMillis() + mqMessageBean.getDelayTime());
                break;
            // Generate Timing message
            case RocketMQServiceConstant.TIMING_MESSAGE:
                long startDeliverTime = RocketMQServiceUtil.getTimestampByDateString(mqMessageBean.getStartDeliveryTime());
                // 如果设置的定时发送时间小于当前时间,则立即发送
                if (startDeliverTime < System.currentTimeMillis()) {
                    startDeliverTime = System.currentTimeMillis();
                }
                msg.setStartDeliverTime(startDeliverTime);
                break;
            // Generate Order message
            case RocketMQServiceConstant.ORDER_MESSAGE:
            default:
                break;
        }

        return msg;
    }
}
