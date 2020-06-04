package com.changgou.search.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Program: ChangGou
 * @ClassName: RabbitMQConfig
 * @Description: 消息配置类
 * @Author: KyleSun
 **/
@Configuration
public class RabbitMQConfig {

    //交换机名称
    public static final String GOODS_UP_EXCHANGE = "goods_up_exchange";

    // 定义队列名称
    public static final String SEARCH_ADD_QUEUE = "search_add_queue";
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";

    // 声明队列
    @Bean
    public Queue queue() {
        return new Queue(AD_UPDATE_QUEUE);
    }

    // 声明队列
    @Bean(SEARCH_ADD_QUEUE)
    public Queue SEARCH_ADD_QUEUE() {
        return new Queue(SEARCH_ADD_QUEUE);
    }

    // 声明交换机
    @Bean(GOODS_UP_EXCHANGE)
    public Exchange GOODS_UP_EXCHANGE() {
        return ExchangeBuilder.fanoutExchange(GOODS_UP_EXCHANGE).durable(true).build();
    }

    // 绑定交换机和队列
    public Binding GOODS_UP_EXCHANGE_BINDING(@Qualifier(SEARCH_ADD_QUEUE) Queue queue, @Qualifier(GOODS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

}
