package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

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
    List<Brand> findAll();

}
