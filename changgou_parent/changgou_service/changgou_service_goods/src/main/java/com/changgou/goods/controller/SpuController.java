package com.changgou.goods.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.service.SpuService;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {


    @Autowired
    private SpuService spuService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Spu> spuList = spuService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", spuList);
    }


    /**
     * @description: //TODO 根据ID查询商品
     * @param: [id]
     * @return: com.changgou.entity.Result
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Goods goods = spuService.findGoodsById(id);
        return new Result(true, StatusCode.OK, "查询成功", goods);
    }


    /***
     * 新增商品
     * @param goods
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Goods goods) {
        spuService.add(goods);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /**
     * @description: //TODO 修改商品信息
     * @param: [spu, id]
     * @return: com.changgou.entity.Result
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Goods goods, @PathVariable String id) {

        if (goods == null) {
            return new Result(false, StatusCode.ERROR, "修改失败，未添加需要修改的商品");
        }

        goods.getSpu().setId(id);
        spuService.update(goods);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Spu> list = spuService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Spu> pageList = spuService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }


    /**
     * @description: //TODO 商品审核
     * @param: [id]
     * @return: com.changgou.entity.Result
     */
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable String id) {
        spuService.audit(id);
        return new Result(true, StatusCode.OK, "商品审核成功");
    }


    /**
     * @description: //TODO 商品下架
     * @param: [id]
     * @return: com.changgou.entity.Result
     */
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable String id) {
        spuService.pull(id);
        return new Result(true, StatusCode.OK, "商品下架成功");
    }

}
