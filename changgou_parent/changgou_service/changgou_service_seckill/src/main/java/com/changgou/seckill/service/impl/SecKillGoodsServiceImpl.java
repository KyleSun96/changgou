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

    public static final String SECKILL_GOODS_STOCK_COUNT_KEY = "seckill_goods_stock_count_";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: //TODO 根据秒杀时间段，查询秒杀商品信息
     * @param: [time]
     * @return: java.util.List<com.changgou.seckill.pojo.SeckillGoods>
     */
    @Override
    public List<SeckillGoods> list(String time) {

        List<SeckillGoods> list = redisTemplate.boundHashOps(SECKILL_GOODS_KEY + time).values();

        // 更新商品库存数据的来源
        for (SeckillGoods seckillGoods : list) {
            // 得到库存数量
            String value = (String) redisTemplate.opsForValue().get(SECKILL_GOODS_STOCK_COUNT_KEY + seckillGoods.getId());
            seckillGoods.setStockCount(Integer.valueOf(value));
        }
        return list;
    }
}
