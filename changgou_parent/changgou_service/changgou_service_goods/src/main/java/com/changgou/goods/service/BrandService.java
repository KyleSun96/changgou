package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @Program: ChangGou
 * @InterfaceName: BrandService
 * @Description:
 * @Author: KyleSun
 **/
public interface BrandService {

    /**
     * @description: //TODO 查询所有品牌
     * @param: []
     * @return: java.util.List<com.changgou.goods.pojo.Brand>
     */
    List<Brand> findList();


    /**
     * @description: //TODO 根据ID查询品牌
     * @param: [id]
     * @return: com.changgou.goods.pojo.Brand
     */
    Brand findById(Integer id);


    /**
     * @description: //TODO 新增品牌
     * @param: [brand]
     * @return: void
     */
    void add(Brand brand);


    /**
     * @description: //TODO 修改品牌数据
     * @param: [brand]
     * @return: void
     */
    void update(Brand brand);


    /**
     * @description: //TODO 根据ID删除品牌数据
     * @param: [id]
     * @return: void
     */
    void delById(Integer id);


    /**
     * @description: //TODO 多条件搜索品牌方法
     * @param: [searchMap]
     * @return: java.util.List<com.changgou.goods.pojo.Brand>
     */
    List<Brand> search(Map<String, Object> searchMap);


    /**
     * @description: //TODO 品牌列表分页查询
     * @param: [page, size]
     * @return: com.github.pagehelper.Page<com.changgou.goods.pojo.Brand>
     */
    Page<Brand> findPage(Integer page, Integer size);


    /**
     * @description: //TODO 品牌列表分页 + 条件查询
     * @param: [searchMap, page, size]
     * @return: com.github.pagehelper.Page<com.changgou.goods.pojo.Brand>
     */
    Page<Brand> findPage(Map<String, Object> searchMap, Integer page, Integer size);

}

