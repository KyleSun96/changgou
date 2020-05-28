package com.changgou.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @GetMapping
    public Result<List<Brand>> findList() {
        List<Brand> brandList = brandService.findList();
        return new Result<>(true, StatusCode.OK, "查询成功", brandList);
    }


    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", brand);
    }


    @PostMapping
    @Transactional
    public Result<Brand> add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result<>(true, StatusCode.OK, "添加成功");
    }


    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    @DeleteMapping("/{id}")
    public Result delById(@PathVariable("id") Integer id) {
        brandService.delById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    @GetMapping("/search")
    public Result<List<Brand>> search(@RequestParam Map<String, Object> searchMap) {
        List<Brand> search = brandService.search(searchMap);
        return new Result<>(true, StatusCode.OK, "查询成功", search);
    }


    @GetMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Brand> pageInfo = brandService.findPage(page, size);
        PageResult<Brand> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @GetMapping("/searchPage/{page}/{size}")
    public Result findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Brand> pageInfo = brandService.findPage(searchMap, page, size);
        PageResult<Brand> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
