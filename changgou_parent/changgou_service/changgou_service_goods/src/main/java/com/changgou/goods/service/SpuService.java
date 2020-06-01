package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SpuService {

    /***
     * 查询所有
     * @return
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

    /***
     * 新增
     * @param goods
     */
    void add(Goods goods);

    /**
     * @description: //TODO 修改商品信息
     * @param: [goods]
     * @return: void
     */
    void update(Goods goods);

    /***
     * 删除
     * @param id
     */
    void delete(String id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Spu> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Spu> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Spu> findPage(Map<String, Object> searchMap, int page, int size);


}
