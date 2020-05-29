package com.changgou.goods.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@CrossOrigin
@RequestMapping("/brand")
@Api(value = "品牌管理", tags = "品牌管理")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @GetMapping
    @ApiOperation("查询所有品牌")
    public Result<List<Brand>> findList() {
        List<Brand> brandList = brandService.findList();
        return new Result<>(true, StatusCode.OK, "查询成功", brandList);
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path", required = true, dataType = "int", allowEmptyValue = false)
    })
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", brand);
    }


    @PostMapping
    @Transactional
    @ApiOperation("新增品牌")
    public Result<Brand> add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result<>(true, StatusCode.OK, "添加成功");
    }


    @PutMapping("/{id}")
    @ApiOperation("修改品牌数据")
    public Result update(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    @DeleteMapping("/{id}")
    @ApiOperation("根据ID删除品牌数据")
    public Result delById(@PathVariable("id") Integer id) {
        brandService.delById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    @GetMapping("/search")
    @ApiOperation("多条件搜索品牌方法")
    public Result<List<Brand>> search(@RequestParam Map<String, Object> searchMap) {
        List<Brand> search = brandService.search(searchMap);
        return new Result<>(true, StatusCode.OK, "查询成功", search);
    }


    @GetMapping("/search/{page}/{size}")
    @ApiOperation("品牌列表分页查询")
    public Result findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Brand> pageInfo = brandService.findPage(page, size);
        PageResult<Brand> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @GetMapping("/searchPage/{page}/{size}")
    @ApiOperation("品牌列表分页 + 条件查询")
    public Result findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Brand> pageInfo = brandService.findPage(searchMap, page, size);
        PageResult<Brand> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }


    @GetMapping("/category/{categoryName}")
    @ApiOperation("根据商品分类名称查询品牌列表")
    public Result findBrandListByCategoryName(@PathVariable("categoryName") String categoryName) {
        List<Map> brandList = brandService.findBrandListByCategoryName(categoryName);
        return new Result(true, StatusCode.OK, "查询成功", brandList);
    }

}
