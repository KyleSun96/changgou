package com.itheima.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.itheima.canal.config.RabbitMQConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: SpuListener
 * @Description: spu监听类
 * @Author: KyleSun
 **/
// 声明当前的类是canal的监听类
@CanalEventListener
public class SpuListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @description: //TODO spu表更新
     * @param: [eventType, rowData] 【对数据库操作的事件类型，对数据库操作的具体数据】
     * @return: void
     */
    @ListenPoint(schema = "changgou_goods", table = {"tb_spu"})
    public void spuUp(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {

        System.err.println("tb_spu表数据发生变化");

        // 获取修改前数据，并转换为 map
        Map<String, String> oldData = new HashMap<>();
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            oldData.put(column.getName(), column.getValue());
        }


        // 获取修改后数据，并转换为 map (λ表达式写法)
        Map<String, String> newData = new HashMap<>();
        rowData.getAfterColumnsList().forEach(column -> newData.put(column.getName(), column.getValue()));


        // 监控最新上架的商品    0 -> 1
        if ("0".equals(oldData.get("is_marketable")) && "1".equals(newData.get("is_marketable"))) {
            // 将商品的 spuId 发送到 mq
            rabbitTemplate.convertAndSend(RabbitMQConfig.GOODS_UP_EXCHANGE, "", newData.get("id"));
        }


        // 监控最新下架的商品    1 -> 0
        if ("1".equals(oldData.get("is_marketable")) && "0".equals(newData.get("is_marketable"))) {
            // 将商品的 spuId 发送到 mq
            rabbitTemplate.convertAndSend(RabbitMQConfig.GOODS_DOWN_EXCHANGE, "", newData.get("id"));
        }


        // 监控最新通过审核的商品    0 -> 1
        if ("0".equals(oldData.get("status")) && "1".equals(newData.get("status"))) {
            // 将商品的 spuId 发送到 mq
            rabbitTemplate.convertAndSend(RabbitMQConfig.GOODS_UP_EXCHANGE, "", newData.get("id"));
        }

    }
}
