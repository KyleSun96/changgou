package com.changgou.goods.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Program: ChangGou
 * @ClassName: BrandController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/findAll")
    public Result<List<Brand>> findAll() {
        List<Brand> brandList = brandService.findAll();
        return new Result<>(true, StatusCode.OK, "查询成功", brandList);
    }


    @GetMapping("findById/{id}")
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", brand);
    }



}
