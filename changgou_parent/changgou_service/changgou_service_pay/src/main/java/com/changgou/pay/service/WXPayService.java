package com.changgou.pay.service;

import java.util.Map;

/**
 * @Program: ChangGou
 * @InterfaceName: WXPayService
 * @Description:
 * @Author: KyleSun
 **/
public interface WXPayService {

    // 本地下单支付
    Map nativePay(String orderId, Integer money);

    // 基于微信查询订单
    Map queryOrder(String orderId);

}
