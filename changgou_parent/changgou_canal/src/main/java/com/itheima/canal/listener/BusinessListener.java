package com.itheima.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.itheima.canal.config.RabbitMQConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Program: ChangGou
 * @ClassName: BusinessListener
 * @Description:
 * @Author: KyleSun
 **/
// 声明当前的类是canal的监听类
@CanalEventListener
public class BusinessListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param eventType 当前操作数据库的类型
     * @param rowData   当前操作数据库的数据
     */
    // 声明操作哪个数据库 以及该数据库中的哪张表
    @ListenPoint(schema = "changgou_business", table = {"tb_ad"})
    public void adUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {

        System.err.println("广告表中数据发生改变！");

        // 获取改变之前的数据
        // rowData.getBeforeColumnsList().forEach((c) -> System.out.println("改变前的数据: " + c.getName() + "::" + c.getValue()));

        // System.out.println("======================");

        // 获取改变之后的数据
        // rowData.getAfterColumnsList().forEach((c) -> System.out.println("改变之后的数据: " + c.getName() + "::" + c.getValue()));


        // 修改前数据
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            if (column.getName().equals("position")) {
                System.out.println("发送消息到mq  ad_update_queue:" + column.getValue());
                // 发送消息到 mq，没有交换机时，路由key使用队列的名称
                rabbitTemplate.convertAndSend("", RabbitMQConfig.AD_UPDATE_QUEUE, column.getValue());
                break;
            }
        }

        // 修改后数据
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if (column.getName().equals("position")) {
                System.out.println("发送消息到mq  ad_update_queue:" + column.getValue());
                // 发送消息到 mq，没有交换机时，路由key使用队列的名称
                rabbitTemplate.convertAndSend("", RabbitMQConfig.AD_UPDATE_QUEUE, column.getValue());
                break;
            }
        }
    }
}
