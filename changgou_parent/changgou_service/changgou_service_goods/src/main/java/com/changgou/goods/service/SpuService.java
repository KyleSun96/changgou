package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SpuService {

    /**
     * @description: //TODO 查询所有
     * @param: []
     * @return: java.util.List<com.changgou.goods.pojo.Spu>
     */
    List<Spu> findAll();

    /**
     * @description: //TODO 根据ID查询Spu
     * @param: [id]
     * @return: com.changgou.goods.pojo.Spu
     */
    Spu findById(String id);

    /**
     * @description: //TODO 根据ID查询商品
     * @param: [id]
     * @return: com.changgou.goods.pojo.Goods
     */
    Goods findGoodsById(String id);

    /**
     * @description: //TODO 新增
     * @param: [goods]
     * @return: void
     */
    void add(Goods goods);

    /**
     * @description: //TODO 修改商品信息
     * @param: [goods]
     * @return: void
     */
    void update(Goods goods);

    /**
     * @description: //TODO 多条件搜索
     * @param: [searchMap]
     * @return: java.util.List<com.changgou.goods.pojo.Spu>
     */
    List<Spu> findList(Map<String, Object> searchMap);

    /**
     * @description: //TODO 分页查询
     * @param: [page, size]
     * @return: com.github.pagehelper.Page<com.changgou.goods.pojo.Spu>
     */
    Page<Spu> findPage(int page, int size);

    /**
     * @description: //TODO 多条件分页查询
     * @param: [searchMap, page, size]
     * @return: com.github.pagehelper.Page<com.changgou.goods.pojo.Spu>
     */
    Page<Spu> findPage(Map<String, Object> searchMap, int page, int size);

    /**
     * @description: //TODO 商品审核
     * @param: [id]
     * @return: void
     */
    void audit(String id);

    /**
     * @description: //TODO 商品下架
     * @param: [id]
     * @return: void
     */
    void pull(String id);

    /**
     * @description: //TODO 商品上架
     * @param: [id]
     * @return: void
     */
    void put(String id);

    /**
     * @description: //TODO 商品逻辑删除
     * @param: [id]
     * @return: void
     */
    void logicDel(String id);

    /**
     * @description: //TODO 恢复逻辑删除的商品
     * @param: [id]
     * @return: void
     */
    void restore(String id);

    /**
     * @description: //TODO 商品物理删除
     * @param: [id]
     * @return: void
     */
    void physicalDel(String id);
}
