package com.changgou.goods.service.impl;

import com.changgou.common.util.PinYinUtils;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

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
    public List<Brand> findList() {
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


    /**
     * @description: //TODO 根据ID删除品牌数据
     * @param: [id]
     * @return: void
     */
    @Override
    public void delById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }


    /**
     * @description: //TODO 多条件搜索品牌方法
     * @param: [searchMap]
     * @return: java.util.List<com.changgou.goods.pojo.Brand>
     */
    @Override
    public List<Brand> search(Map<String, Object> searchMap) {

        Example example = new Example(Brand.class);
        // 封装查询条件
        Example.Criteria criteria = example.createCriteria();
        // where name = 'admin'    字段  条件  值
        if (searchMap != null) {
            // 品牌名称(模糊) like  %
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 按照品牌首字母进行查询(精确)
            if (searchMap.get("letter") != null && !"".equals(searchMap.get("letter"))) {
                criteria.andEqualTo("letter", searchMap.get("letter"));
            }
        }

        return brandMapper.selectByExample(example);
    }


    /**
     * @description: //TODO 品牌列表分页查询
     * @param: [page, size]
     * @return: com.github.pagehelper.Page<com.changgou.goods.pojo.Brand>
     */
    @Override
    public Page<Brand> findPage(Integer page, Integer size) {

        PageHelper.startPage(page, size);
        Page<Brand> brandPage = (Page<Brand>) brandMapper.selectAll();

        return brandPage;
    }
}
