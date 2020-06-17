package com.changgou.seckill.service.impl;

import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: ChangGou
 * @ClassName: SecKillGoodsServiceImpl
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class SecKillGoodsServiceImpl implements SecKillGoodsService {

    public static final String SECKILL_GOODS_KEY = "seckill_goods_";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: //TODO 根据秒杀时间段，查询秒杀商品信息
     * @param: [time]
     * @return: java.util.List<com.changgou.seckill.pojo.SeckillGoods>
     */
    @Override
    public List<SeckillGoods> list(String time) {

        return (List<SeckillGoods>) redisTemplate.boundHashOps(SECKILL_GOODS_KEY + time).values();
    }
}
