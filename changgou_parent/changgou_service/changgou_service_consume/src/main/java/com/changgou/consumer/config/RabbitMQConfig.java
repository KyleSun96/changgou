package com.changgou.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Program: ChangGou
 * @ClassName: RabbitMQConfig
 * @Description:
 * @Author: KyleSun
 **/
@Configuration
public class RabbitMQConfig {

    public static final String SECKILL_ORDER_QUEUE = "seckill_order";

    // 开启队列持久化
    @Bean
    public Queue queue() {
        return new Queue(SECKILL_ORDER_QUEUE, true);
    }

}
