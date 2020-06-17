package com.changgou.seckill.feign;

import com.changgou.entity.Result;
import com.changgou.seckill.pojo.SeckillGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seckill")
public interface SecKillGoodsFeign {

    // 根据秒杀时间段，查询秒杀商品信息
    @RequestMapping("/seckillgoods/list")
    public Result<List<SeckillGoods>> list(@RequestParam("time") String time);

}
