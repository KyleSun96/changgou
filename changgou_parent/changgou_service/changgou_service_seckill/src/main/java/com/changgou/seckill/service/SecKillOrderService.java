package com.changgou.seckill.service;

/**
 * @Program: ChangGou
 * @InterfaceName: SecKillOrderService
 * @Description:
 * @Author: KyleSun
 **/
public interface SecKillOrderService {

    // 秒杀异步下单
    boolean add(Long id, String time, String username);

}
