package com.changgou.seckill.dao;

import com.changgou.seckill.pojo.SeckillOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


public interface SecKillOrderMapper extends Mapper<SeckillOrder> {


    // 根据用户名和商品ID查询秒杀订单信息
    @Select("SELECT * FROM `tb_seckill_order` WHERE user_id = #{username} AND seckill_id = #{id}")
    SeckillOrder getOrderInfoByUserNameAndGoodsId(@Param("username") String username, @Param("id") Long id);
}
