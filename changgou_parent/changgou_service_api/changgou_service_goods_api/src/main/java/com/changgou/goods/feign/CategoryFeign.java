package com.changgou.goods.feign;

import com.changgou.entity.Result;
import com.changgou.goods.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Program: ChangGou
 * @InterfaceName: CategoryFeign
 * @Description:
 * @Author: KyleSun
 **/
@FeignClient(name = "goods")    // 声明当前feign操作goods商品服务
public interface CategoryFeign {

    @GetMapping("/category/{id}")
    public Result<Category> findById(@PathVariable("id") Integer id);

}
