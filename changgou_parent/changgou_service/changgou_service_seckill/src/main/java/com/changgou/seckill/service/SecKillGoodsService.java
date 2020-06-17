package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillGoods;

import java.util.List;

/**
 * @Program: ChangGou
 * @InterfaceName: SecKillGoodsService
 * @Description:
 * @Author: KyleSun
 **/
public interface SecKillGoodsService {


    /**
     * @description: //TODO 根据秒杀时间段，查询秒杀商品信息
     * @param: []
     * @return: java.util.List<com.changgou.seckill.pojo.SeckillGoods>
     */
    List<SeckillGoods> list(String time);

}
