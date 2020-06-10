package com.changgou.order.service;

import java.util.Map;

/**
 * @Program: ChangGou
 * @InterfaceName: CartService
 * @Description: 购物车服务
 * @Author: KyleSun
 **/
public interface CartService {


    /**
     * @description: //TODO 添加购物车
     * @param: [skuId, num, username]【商品skuId，商品数量，用户名】
     * @return: void
     */
    void addCart(String skuId, Integer num, String username);


    /**
     * @description: //TODO 查询购物车数据
     * @param: [username]
     * @return: java.util.Map
     * <p>
     * 使用map的原因：返回的数据不止有商品数据，还有商品价格、总数 等数据
     */
    Map list(String username);

}
