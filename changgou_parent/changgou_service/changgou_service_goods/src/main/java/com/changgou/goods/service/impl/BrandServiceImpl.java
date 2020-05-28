package com.changgou.goods.service.impl;

import com.changgou.common.util.PinYinUtils;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import org.apache.commons.lang.StringUtils;
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


    /**
     * @description: //TODO 新增品牌
     * @param: [brand]
     * @return: void
     */
    @Override
    public void add(Brand brand) {

        String name = brand.getName();
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("参数非法，品牌名不能为空！");
        }

        // 判断 填写品牌的首字母
        String letter = brand.getLetter();
        // 如果没传 letter 参数
        if (StringUtils.isBlank(letter)) {
            letter = PinYinUtils.getFirstLetter(name);  // name ? null
        } else {
            // 转成大写存储到数据库
            letter = letter.toUpperCase();
        }
        brand.setLetter(letter);

        brandMapper.insertSelective(brand);
    }


    /**
     * @description: //TODO 修改品牌数据
     * @param: [brand]
     * @return: void
     */
    @Override
    public void update(Brand brand) {

        // update brand set name=?,image=?,letter=?,seq=? where id=?
        brandMapper.updateByPrimaryKey(brand);

        // update brand set if(name!=null )name=? where id=?
        //  brandMapper.updateByPrimaryKeySelective(brand);
    }
}
