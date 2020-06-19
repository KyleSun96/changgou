package com.changgou.seckill.web.controller;

import com.changgou.entity.Result;
import com.changgou.seckill.feign.SecKillOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: ChangGou
 * @ClassName: SecKillOrderController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/wseckillorder")
public class SecKillOrderController {

    @Autowired
    private SecKillOrderFeign secKillOrderFeign;

    /**
     * @description: //TODO 秒杀异步下单
     * @param: [time, id]
     * @return: com.changgou.entity.Result
     */
    @RequestMapping("/add")
    public Result add(@RequestParam("time") String time, @RequestParam("id") Long id) {
        return secKillOrderFeign.add(time, id);
    }

}
