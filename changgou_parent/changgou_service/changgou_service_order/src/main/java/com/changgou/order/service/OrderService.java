package com.changgou.order.service;

import com.changgou.order.pojo.Order;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /***
     * 查询所有
     * @return
     */
    List<Order> findAll();

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * @description: //TODO 新增订单
     * @param: [order]
     * @return: void
     */
    String add(Order order);

    /***
     * 修改
     * @param order
     */
    void update(Order order);

    /***
     * 删除
     * @param id
     */
    void delete(String id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Order> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Order> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Order> findPage(Map<String, Object> searchMap, int page, int size);


    /**
     * @description: //TODO 用户付款后，完成订单数据库的修改
     * @param: [orderId, transactionId]
     * @return: void
     */
    void updatePayStatus(String orderId, String transactionId);


    /**
     * @description: //TODO 关闭订单
     * @param: [message]
     * @return: void
     */
    void closeOrder(String orderId);


    /**
     * @description: //TODO 批量发货
     * @param: [orders]【订单集合】
     * @return: void
     */
    void batchSend(List<Order> orders);


    /**
     * @description: //TODO 手动确认收货（用户收货后，15天内已确认）
     * @param: [orderId, operator]
     * @return: void
     */
    void manualConfirm(String orderId, String operator);


    /**
     * @description: //TODO 自动确认收货（用户收货后，15天内未确认）
     * @param: [orderId, operator]
     * @return: void
     */
    void autoConfirm();

}
