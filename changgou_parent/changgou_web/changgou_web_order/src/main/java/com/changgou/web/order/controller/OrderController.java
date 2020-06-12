package com.changgou.web.order.controller;

import com.changgou.order.feign.CartFeign;
import com.changgou.order.pojo.OrderItem;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
