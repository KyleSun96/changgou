package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: ChangGou
 * @ClassName: BrandServiceImpl
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * @description: //TODO 查询所有品牌
     * @param: []
     * @return: java.util.List<com.changgou.goods.pojo.Brand>
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }


    /**
     * @description: //TODO 根据ID查询
     * @param: [id]
     * @return: com.changgou.goods.pojo.Brand
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
