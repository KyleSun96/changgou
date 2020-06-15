package com.changgou.pay.service.impl;

import com.changgou.pay.service.WXPayService;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: WXPayServiceImpl
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class WXPayServiceImpl implements WXPayService {

    @Autowired
    private WXPay wxPay;

    @Value("${wxpay.notify_url}")
    private String notify_url;

    /**
     * @description: //TODO 本地下单支付
     * @param: [orderId, money]
     * @return: java.util.Map
     */
    @Override
    public Map nativePay(String orderId, Integer money) {

        try {
            // 1. 封装请求参数（基于微信开发文档的要求）
            Map<String, String> map = new HashMap<>();
            map.put("body", "畅购商品支付");
            map.put("out_trade_no", orderId);

            // 微信订单金额单位为分，且为防止精度丢失，使用BigDecimal
            // 此处指定商品金额以便支付测试
            BigDecimal payMoney = new BigDecimal("0.01");   // 单位：元
            BigDecimal fen = payMoney.multiply(new BigDecimal("100")); // 单位：分 --> 1.00
            fen = fen.setScale(0, BigDecimal.ROUND_UP); // 保留小数点后0位，并向上取整：1

            map.put("total_fee", String.valueOf(fen));

            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", notify_url);    // 异步接收微信支付结果通知的回调地址
            map.put("trade_type", "NATIVE");

            // 2.基于wxpay完成统一下单接口的调用，并获取返回结果
            return wxPay.unifiedOrder(map);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
