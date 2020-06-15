package com.changgou.pay.feign;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: ChangGou
 * @InterfaceName: WXPayFeign
 * @Description:
 * @Author: KyleSun
 **/
@FeignClient(name = "pay")
public interface WXPayFeign {

    // 本地下单支付
    @GetMapping("/wxpay/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money);

}