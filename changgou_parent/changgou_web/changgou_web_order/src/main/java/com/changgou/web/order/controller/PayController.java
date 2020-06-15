package com.changgou.web.order.controller;

import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.pay.feign.WXPayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: PayController
 * @Description:
 * @Author: KyleSun
 **/
@Controller
@RequestMapping("/wxpay")
public class PayController {

    @Autowired
    private WXPayFeign wxPayFeign;

    @Autowired
    private OrderFeign orderFeign;

    /**
     * @description: //TODO 跳转到微信二维码支付页面
     * @param: [orderId]
     * @return: java.lang.String
     */
    @RequestMapping
    public String wxPay(String orderId, Model model) {

        Order order = orderFeign.findById(orderId).getData();

        // 1.根据orderId查询订单,如果订单不存在,跳转到错误页面
        if (order == null) {
            return "fail";
        }

        // 2.根据订单的支付状态进行判断,如果不是未支付的订单,跳转到错误页面
        if (!"0".equals(order.getPayStatus())) {
            return "fail";
        }

        // 3.基于payFeign调用统计下单接口,并获取返回结果
        Map payMap = (Map) wxPayFeign.nativePay(orderId, order.getPayMoney()).getData();
        if (payMap == null) {
            // 微信无信息返回，说明支付时发生错误
            return "fail";
        }

        // 4.封装结果数据
        payMap.put("orderId", orderId);
        payMap.put("payMoney", order.getPayMoney());

        // 5.跳转页面时携带数据
        model.addAllAttributes(payMap);

        return "wxpay";
    }


    /**
     * @description: //TODO 支付成功后页面的跳转
     * @param: [payMoney, model]
     * @return: java.lang.String
     */
    @RequestMapping("/toPaySuccess")
    public String toPaySuccess(Integer payMoney, Model model) {
        model.addAttribute("payMoney", payMoney);
        return "paysuccess";
    }


}
