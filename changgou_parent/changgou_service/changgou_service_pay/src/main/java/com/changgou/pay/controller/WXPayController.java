package com.changgou.pay.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.pay.service.WXPayService;
import com.changgou.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: WXPayController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/wxpay")
public class WXPayController {


    @Autowired
    private WXPayService wxPayService;


    /**
     * @description: //TODO 本地下单支付
     * @param: [orderId, money]
     * @return: com.changgou.entity.Result
     */
    @GetMapping("/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money) {

        Map resultMap = wxPayService.nativePay(orderId, money);
        return new Result(true, StatusCode.OK, "", resultMap);

    }


    /**
     * @description: //TODO 消费者扫码付款成功后，商家接收微信发出的回调信息
     * @param: []
     * @return: void
     */
    @RequestMapping("/notify")
    public void notifyLogic(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("支付成功回调消息！");
        try {
            // 1.获取微信返回的回调信息，为输入流格式
            ServletInputStream is = request.getInputStream();
            String xml = ConvertUtils.convertToString(is);
            System.out.println(xml);

            // 2.给微信一个结果通知
            response.setContentType("text/xml");
            String receipt = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            response.getWriter().write(receipt);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
