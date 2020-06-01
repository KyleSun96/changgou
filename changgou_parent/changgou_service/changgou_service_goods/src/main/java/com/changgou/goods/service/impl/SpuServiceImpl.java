package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.dao.*;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.SpuService;
import com.changgou.util.IdWorker;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;


    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * @description: //TODO 根据ID查询Spu
     * @param: [id]
     * @return: com.changgou.goods.pojo.Spu
     */
    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * @description: //TODO 根据ID查询商品
     * @param: [id]
     * @return: com.changgou.goods.pojo.Goods
     */
    @Override
    public Goods findGoodsById(String id) {

        // 1. 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);

        // 2. 查询sku
        // 声明当前example操作的实体类
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);

        // 3. 封装到goods
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);

        return goods;
    }

    /**
     * @description: //TODO 新增商品
     * @param: [goods]
     * @return: void
     */
    @Override
    @Transactional
    public void add(Goods goods) {

        // 1. 添加 spu
        this.saveSpu(goods);

        // 2. 添加 sku 集合
        this.saveSkuList(goods);

    }

    /**
     * @description: //TODO 添加 spu
     * @param: [goods]
     * @return: void
     */
    private void saveSpu(Goods goods) {

        Spu spu = goods.getSpu();
        // 设置分布式ID
        long spuId = idWorker.nextId();
        spu.setId(String.valueOf(spuId));
        // 设置删除状态
        spu.setIsDelete("0");
        // 设置上架状态
        spu.setIsMarketable("0");
        // 设置审核状态
        spu.setStatus("0");

        spuMapper.insertSelective(spu);

    }

    /**
     * @description: //TODO 添加 sku 集合
     * @param: [goods]
     * @return: void
     */
    private void saveSkuList(Goods goods) {

        // 设置品牌与分类的关联关系
        this.setCategoryBrand(goods);

        // 获取 spu 对象
        Spu spu = goods.getSpu();
        // 获取当前日期
        Date date = new Date();
        // 获取分类
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        // 获取品牌
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        // 获取sku集合对象
        List<Sku> skuList = goods.getSkuList();

        // 前端数据存在不传skuList的可能性，因此需要添加判断以防出现空指针异常
        if (skuList != null) {
            for (Sku sku : skuList) {
                // 设置sku主键 ID
                sku.setId(String.valueOf(idWorker.nextId()));
                // 设置sku规格
                if (sku.getSpec() == null || "".equals(sku.getSpec())) {
                    // 如果规格参数为空或者为null，先设置为 {}，以防出现空指针异常
                    sku.setSpec("{}");
                }

                // 设置 sku 的名称（商品名称 + 规格信息）拼接
                String name = spu.getName();
                // 将规格的json字符串转为 map
                Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                // 遍历规格 map 的 values，获取其中的规格参数
                if (specMap != null && specMap.size() > 0) {
                    // sku名 = spu名 + 规格参数
                    for (String value : specMap.values()) {
                        name += " " + value;
                    }
                }

                sku.setName(name);
                sku.setSpuId(spu.getId());  //设置spu的ID
                sku.setCreateTime(date);    //创建日期
                sku.setUpdateTime(date);    //修改日期
                sku.setCategoryId(category.getId());    //商品分类ID
                sku.setCategoryName(category.getName());//商品分类名称
                sku.setBrandName(brand.getName());      //品牌名称
                skuMapper.insertSelective(sku);         //插入sku表数据

            }
        }

    }


    /**
     * @description: //TODO 设置品牌与分类的关联关系
     * @param: [goods]
     * @return: void
     */
    private void setCategoryBrand(Goods goods) {

        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setCategoryId(goods.getSpu().getCategory3Id());
        categoryBrand.setBrandId(goods.getSpu().getBrandId());

        int count = categoryBrandMapper.selectCount(categoryBrand);

        if (count == 0) {
            // 如果没有关系数据则添加品牌和分类关系
            categoryBrandMapper.insert(categoryBrand);
        }
    }


    /**
     * @description: //TODO 修改商品信息
     * @param: [spu]
     * @return: void
     */
    @Override
    @Transactional
    public void update(Goods goods) {

        // 修改spu
        Spu spu = goods.getSpu();
        spuMapper.updateByPrimaryKey(spu);

        // 删除原sku列表
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", spu.getId());

        skuMapper.deleteByExample(example);

        // 修改sku
        this.saveSkuList(goods);

    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Spu>) spuMapper.selectAll();
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Spu>) spuMapper.selectByExample(example);
    }


    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 主键
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 货号
            if (searchMap.get("sn") != null && !"".equals(searchMap.get("sn"))) {
                criteria.andEqualTo("sn", searchMap.get("sn"));
            }
            // SPU名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 副标题
            if (searchMap.get("caption") != null && !"".equals(searchMap.get("caption"))) {
                criteria.andLike("caption", "%" + searchMap.get("caption") + "%");
            }
            // 图片
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 图片列表
            if (searchMap.get("images") != null && !"".equals(searchMap.get("images"))) {
                criteria.andLike("images", "%" + searchMap.get("images") + "%");
            }
            // 售后服务
            if (searchMap.get("saleService") != null && !"".equals(searchMap.get("saleService"))) {
                criteria.andLike("saleService", "%" + searchMap.get("saleService") + "%");
            }
            // 介绍
            if (searchMap.get("introduction") != null && !"".equals(searchMap.get("introduction"))) {
                criteria.andLike("introduction", "%" + searchMap.get("introduction") + "%");
            }
            // 规格列表
            if (searchMap.get("specItems") != null && !"".equals(searchMap.get("specItems"))) {
                criteria.andLike("specItems", "%" + searchMap.get("specItems") + "%");
            }
            // 参数列表
            if (searchMap.get("paraItems") != null && !"".equals(searchMap.get("paraItems"))) {
                criteria.andLike("paraItems", "%" + searchMap.get("paraItems") + "%");
            }
            // 是否上架
            if (searchMap.get("isMarketable") != null && !"".equals(searchMap.get("isMarketable"))) {
                criteria.andEqualTo("isMarketable", searchMap.get("isMarketable"));
            }
            // 是否启用规格
            if (searchMap.get("isEnableSpec") != null && !"".equals(searchMap.get("isEnableSpec"))) {
                criteria.andEqualTo("isEnableSpec", searchMap.get("isEnableSpec"));
            }
            // 是否删除
            if (searchMap.get("isDelete") != null && !"".equals(searchMap.get("isDelete"))) {
                criteria.andEqualTo("isDelete", searchMap.get("isDelete"));
            }
            // 审核状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }

            // 品牌ID
            if (searchMap.get("brandId") != null) {
                criteria.andEqualTo("brandId", searchMap.get("brandId"));
            }
            // 一级分类
            if (searchMap.get("category1Id") != null) {
                criteria.andEqualTo("category1Id", searchMap.get("category1Id"));
            }
            // 二级分类
            if (searchMap.get("category2Id") != null) {
                criteria.andEqualTo("category2Id", searchMap.get("category2Id"));
            }
            // 三级分类
            if (searchMap.get("category3Id") != null) {
                criteria.andEqualTo("category3Id", searchMap.get("category3Id"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
            // 运费模板id
            if (searchMap.get("freightId") != null) {
                criteria.andEqualTo("freightId", searchMap.get("freightId"));
            }
            // 销量
            if (searchMap.get("saleNum") != null) {
                criteria.andEqualTo("saleNum", searchMap.get("saleNum"));
            }
            // 评论数
            if (searchMap.get("commentNum") != null) {
                criteria.andEqualTo("commentNum", searchMap.get("commentNum"));
            }

        }
        return example;
    }

    /**
     * @description: //TODO 商品审核
     * @param: [id]
     * @return: void
     */
    @Override
    public void audit(String id) {
        // 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);

        if (spu == null) {
            throw new RuntimeException("当前商品不存在");
        }

        // 判断当前spu是否处于删除,删除状态不可审核
        if ("1".equals(spu.getIsDelete())) {
            throw new RuntimeException("当前商品处于删除状态");
        }
        // 不处于删除状态的商品，修改审核状态为(1)，并且自动上架
        spu.setStatus("1");
        spu.setIsMarketable("1");

        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * @description: //TODO 商品下架
     * @param: [id]
     * @return: void
     */
    @Override
    public void pull(String id) {
        // 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);

        if (spu == null) {
            throw new RuntimeException("当前商品不存在");
        }

        // 判断当前spu是否处于删除,删除状态不可下架
        if ("1".equals(spu.getIsDelete())) {
            throw new RuntimeException("当前商品处于删除状态");
        }
        // 不处于删除状态的商品，修改为下架状态 (0)
        spu.setIsMarketable("0");

        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * @description: //TODO 商品上架
     * @param: [id]
     * @return: void
     */
    @Override
    public void put(String id) {
        // 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);

        if (spu == null) {
            throw new RuntimeException("当前商品不存在");
        }

        // 判断当前spu是否审核,未审核状态不可上架
        if ("0".equals(spu.getStatus())) {
            throw new RuntimeException("未通过审核的商品不能上架！");
        }
        // 通过审核的商品，修改为上架状态(1)
        spu.setIsMarketable("1");

        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * @description: //TODO 逻辑删除商品
     * @param: [id]
     * @return: void
     */
    @Override
    public void logicDel(String id) {
        // 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        // 已下架的商品才能执行逻辑删除
        if (!spu.getIsMarketable().equals("0")) {
            throw new RuntimeException("商品必须先下架后再删除！");
        }
        spu.setIsDelete("1");

        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * @description: //TODO 恢复逻辑删除的商品
     * @param: [id]
     * @return: void
     */
    @Override
    public void restore(String id) {
        // 查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        // 已删除的商品才能执行恢复
        if (!spu.getIsDelete().equals("1")) {
            throw new RuntimeException("未删除商品不需要恢复！");
        }
        spu.setIsDelete("0");

        spuMapper.updateByPrimaryKeySelective(spu);
    }

}
