package com.changgou.search.listener;

import com.changgou.search.config.RabbitMQConfig;
import com.changgou.search.service.ESManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Program: ChangGou
 * @ClassName: GoodsUpListener
 * @Description: 商品上架rabbitMQ监听类
 * @Author: KyleSun
 **/
@Component
public class GoodsUpListener {

    @Autowired
    private ESManagerService esManagerService;

    // 指定需要操作的队列
    @RabbitListener(queues = RabbitMQConfig.SEARCH_ADD_QUEUE)
    public void receiveMessage(String spuId) {
        System.out.println("接收到的消息为: " + spuId);

        // 查询skuList,并导入到索引库
        esManagerService.importDataBySpuId(spuId);
    }
}

