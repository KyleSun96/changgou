package com.changgou.task;

import com.changgou.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Program: ChangGou
 * @ClassName: OrderTask
 * @Description:
 * @Author: KyleSun
 **/
@Component
public class OrderTask {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * @description: //TODO 自动确认收货
     * @param: cron = "0 0 0 * * ?" 每天执行一次
     * @return: void
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoTake() {

        System.out.println(new Date());
        rabbitTemplate.convertAndSend("", RabbitMQConfig.ORDER_TACK, "随意发送一条消息");

    }
}
