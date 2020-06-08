package com.changgou.page.listener;

import com.changgou.page.config.RabbitMQConfig;
import com.changgou.page.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Program: ChangGou
 * @ClassName: PageListener
 * @Description: 消息监听类
 * @Author: KyleSun
 **/
@Component
public class PageListener {

    @Autowired
    private PageService pageService;

    // 指定监听队列
    @RabbitListener(queues = RabbitMQConfig.PAGE_CREATE_QUEUE)
    public void receiveMessage(String spuId) {

        System.out.println("获取静态化页面的商品id,id的值为:   " + spuId);
        // 调用业务层，完成静态化页面生成
        pageService.generateHTML(spuId);
    }
}
