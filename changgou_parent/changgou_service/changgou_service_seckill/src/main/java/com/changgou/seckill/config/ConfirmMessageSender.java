package com.changgou.seckill.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Program: ChangGou
 * @ClassName: ConfirmMessageSender
 * @Description: 该类用于增强rabbitTemplate
 * @Author: KyleSun
 **/
@Component
public class ConfirmMessageSender implements RabbitTemplate.ConfirmCallback {

    public static final String MESSAGE_CONFIRM_KEY = "message_confirm_";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public ConfirmMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate.setConfirmCallback(this)：设置消息返回的对象【返回给自己】，因为本类中实现confirm接收方法
        rabbitTemplate.setConfirmCallback(this);
    }


    /**
     * @description: //TODO 自定义消息发送方法
     * @param: [exchange, routingKey, message]
     * @return: void
     */
    public void sendMessage(String exchange, String routingKey, String message) {

        // CorrelationData设置消息的唯一标识并存入到redis中
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        redisTemplate.opsForValue().set(correlationData.getId(), message);

        // 将本次发送消息的相关元数据保存到redis中
        Map<String, String> map = new HashMap<>();
        map.put("exchange", exchange);
        map.put("routingKey", routingKey);
        map.put("message", message);
        redisTemplate.opsForHash().putAll(MESSAGE_CONFIRM_KEY + correlationData.getId(), map);

        // 携带着本次消息的唯一标识，进行数据发送
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);

    }


    /**
     * @description: //TODO 用于接收消息服务器返回的通知
     * @param: [correlationData, ack, cause]【消息的唯一标识，成功/失败标识，？？？】
     * @return: void
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if (ack) {
            // 成功通知
            // 删除消息
            // 删除redis中的相关数据
            redisTemplate.delete(correlationData.getId());
            redisTemplate.delete(MESSAGE_CONFIRM_KEY + correlationData.getId());
        } else {
            // 失败通知
            // 从redis中获取刚才的消息内容
            // 从redis取出整个map：redisTemplate.opsForHash().entries()，其中 entries(需要取出的map的key)
            Map<String, String> map = (Map<String, String>) redisTemplate.opsForHash().entries(MESSAGE_CONFIRM_KEY + correlationData.getId());
            // 重新发送
            String exchange = map.get("exchange");
            String routingkey = map.get("routingkey");
            String message = map.get("message");

            this.sendMessage(exchange, routingkey, message);
        }
    }
}
