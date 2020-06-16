package com.changgou.goods.feign;

import com.changgou.entity.Result;
import com.changgou.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Program: ChangGou
 * @InterfaceName: SkuFeign
 * @Description:
 * @Author: KyleSun
 **/
@FeignClient(name = "goods")
public interface SkuFeign {

    @GetMapping("/sku/spu/{spuId}")
    public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);


    /**
     * @description: //TODO 根据skuId查询sku信息
     * @param: [id]
     * @return: com.changgou.entity.Result<com.changgou.goods.pojo.Sku>
     */
    @GetMapping("/sku/{id}")
    public Result<Sku> findById(@PathVariable("id") String id);


    /**
     * @description: //TODO 扣减库存，添加销量
     * @param: [username]
     * @return: com.changgou.entity.Result
     */
    @PostMapping("/sku/decr/count")
    public Result decrCount(@RequestParam("username") String username);


    /**
     * @description: //TODO 回滚库存，扣减销量
     * @param: [skuId, num]
     * @return: com.changgou.entity.Result
     */
    @RequestMapping("/sku/resumeStockNum")
    public Result resumeStockNum(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num);


}
