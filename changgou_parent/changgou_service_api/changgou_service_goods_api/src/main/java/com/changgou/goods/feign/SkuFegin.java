package com.changgou.goods.feign;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Program: ChangGou
 * @InterfaceName: SkuFegin
 * @Description:
 * @Author: KyleSun
 * @Create: 20:53 2020/6/4
 **/
@FeignClient(name = "goods")
public interface SkuFegin {

    @GetMapping("/sku/spu/{spuId}")
    public Result findSkuListBySpuId(@PathVariable("spuId") String spuId);
}
