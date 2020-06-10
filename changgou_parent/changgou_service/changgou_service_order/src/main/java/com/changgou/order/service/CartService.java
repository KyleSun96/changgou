package com.changgou.order.service;

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
}
