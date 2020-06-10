package com.changgou.order.feign;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Program: ChangGou
 * @InterfaceName: CartFeign
 * @Description:
 * @Author: KyleSun
 **/
@FeignClient(name = "order")
public interface CartFeign {

    // 添加购物车
    @GetMapping("/cart/addCart")
    public Result addCart(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num);

    // 查询购物车数据
    @GetMapping("/cart/list")
    public Map list();
}