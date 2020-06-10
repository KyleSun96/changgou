package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: CartController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * @description: //TODO 添加购物车
     * @param: [skuId, num]
     * @return: com.changgou.entity.Result
     */
    @GetMapping("/addCart")
    public Result addCart(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num) {

        // 动态获取用户名，暂时使用静态数据代替测试
        // String username = "testName";
        String username = tokenDecode.getUserInfo().get("username");
        cartService.addCart(skuId, num, username);

        return new Result(true, StatusCode.OK, "加入购物车成功!");
    }


    /**
     * @description: //TODO 查询购物车数据
     * @param: []
     * @return: java.util.Map
     */
    @GetMapping("/list")
    public Map list() {
        // 动态获取用户名，暂时使用静态数据代替测试
        // String username = "testName";
        String username = tokenDecode.getUserInfo().get("username");
        return cartService.list(username);
    }


}
