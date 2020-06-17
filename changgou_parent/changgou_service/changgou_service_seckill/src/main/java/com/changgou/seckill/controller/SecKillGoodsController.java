package com.changgou.seckill.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Program: ChangGou
 * @ClassName: SecKillGoodsController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/seckillgoods")
public class SecKillGoodsController {


    @Autowired
    private SecKillGoodsService secKillGoodsService;


    /**
     * @description: //TODO 根据秒杀时间段，查询秒杀商品信息
     * @param: [time]
     * @return: com.changgou.entity.Result<java.util.List < com.changgou.seckill.pojo.SeckillGoods>>
     * <p>
     * 该模块已经对接oauth2，但是查看秒杀商品列表不需要传递令牌，因此需要放行该方法
     */
    @RequestMapping("/list")
    public Result<List<SeckillGoods>> list(@RequestParam("time") String time) {
        List<SeckillGoods> seckillGoodsList = secKillGoodsService.list(time);
        return new Result<>(true, StatusCode.OK, "查询秒杀商品成功", seckillGoodsList);
    }


}
