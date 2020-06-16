package com.changgou.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Program: ChangGou
 * @ClassName: RabbitMQConfig
 * @Description:
 * @Author: KyleSun
 **/
@Component
public class RabbitMQConfig {

    // 自动收货通知队列
    public static final String ORDER_TACK = "order_tack";

    @Bean
    public Queue queue() {
        return new Queue(ORDER_TACK);
    }

}
