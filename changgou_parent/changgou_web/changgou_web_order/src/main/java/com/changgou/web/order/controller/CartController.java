package com.changgou.web.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.CartFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: CartController
 * @Description:
 * @Author: KyleSun
 **/
@Controller
@RequestMapping("/wcart")
public class CartController {

    @Autowired
    private CartFeign cartFeign;


    /**
     * @description: //TODO 查询购物车数据用于页面渲染
     * @param: []
     * @return: java.util.Map
     */
    @GetMapping("/list")
    public String list(Model model) {

        Map map = cartFeign.list();
        // 跳转页面时携带查询到的购物车数据
        model.addAttribute("items", map);

        return "cart";
    }


    /**
     * @description: //TODO 添加购物车
     * @param: [skuId, num]
     * @return: com.changgou.entity.Result
     */
    @ResponseBody
    @GetMapping("/add")
    public Result<Map> addCart(String skuId, Integer num) {

        cartFeign.addCart(skuId, num);
        // 添加购物车后需要重新更新数据
        Map map = cartFeign.list();
        return new Result<>(true, StatusCode.OK, "添加购物车成功", map);

    }

}
