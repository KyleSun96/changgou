package com.changgou.web.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.CartFeign;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: OrderController
 * @Description:
 * @Author: KyleSun
 **/
@Controller
@RequestMapping("/worder")
public class OrderController {

    @Autowired
    private AddressFeign addressFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private OrderFeign orderFeign;


    /**
     * @description: //TODO 携带数据并渲染提交订单页面
     * @param: []
     * @return: java.lang.String
     */
    @RequestMapping("/ready/order")
    public String readyOrder(Model model) {

        // 获取收件人的地址信息
        List<Address> addressList = addressFeign.list().getData();
        model.addAttribute("address", addressList);

        // 获取购物车信息
        Map map = cartFeign.list();
        List<OrderItem> orderItemList = (List<OrderItem>) map.get("orderItemList");
        Integer totalMoney = (Integer) map.get("totalMoney");
        Integer totalNum = (Integer) map.get("totalNum");
        model.addAttribute("carts", orderItemList);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalNum", totalNum);

        // 未选择收件人时，底部初始化加载默认的收件人
        for (Address address : addressList) {
            if ("1".equals(address.getIsDefault())) {
                // deAddr --> default address 默认收件人
                model.addAttribute("deAddr", address);
                break;
            }
        }
        return "order";
    }


    /**
     * @description: //TODO 新增订单数据
     * @param: [order]
     * @return: com.changgou.entity.Result
     */
    @ResponseBody
    @PostMapping("/add")
    public Result add(@RequestBody Order order) {
        return orderFeign.add(order);
    }


    /**
     * @description: //TODO 跳转支付页面
     * @param: [orderId]
     * @return: java.lang.String
     */
    @GetMapping("/toPayPage")
    public String toPayPage(String orderId, Model model) {

        // 获取订单信息
        Order order = orderFeign.findById(orderId).getData();
        // 跳转页面时携带数据
        model.addAttribute("orderId", orderId);
        model.addAttribute("payMoney", order.getPayMoney());
        return "pay";
    }

}
