package com.changgou.consumer.service.impl;

import com.changgou.consumer.dao.SecKillGoodsMapper;
import com.changgou.consumer.dao.SecKillOrderMapper;
import com.changgou.consumer.service.SecKillOrderService;
import com.changgou.seckill.pojo.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Program: ChangGou
 * @ClassName: SecKillOrderServiceImpl
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class SecKillOrderServiceImpl implements SecKillOrderService {

    @Autowired
    private SecKillGoodsMapper secKillGoodsMapper;

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;


    /**
     * @description: //TODO 同步mysql中的数据
     * @param: [seckillOrder]
     * @return: int
     */
    @Override
    @Transactional
    public int createOrder(SeckillOrder seckillOrder) {

        // 同步mysql中的数据
        // 1.扣减秒杀商品的库存
        int count = secKillGoodsMapper.updateStockCount(seckillOrder.getSeckillId());
        if (count <= 0) {
            return 0;
        }

        // 2.新增秒杀订单
        count = secKillOrderMapper.insertSelective(seckillOrder);
        if (count <= 0) {
            return 0;
        }
        return 0;
    }

}
