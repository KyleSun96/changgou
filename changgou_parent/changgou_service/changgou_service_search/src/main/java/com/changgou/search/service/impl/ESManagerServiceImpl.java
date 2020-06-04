package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.ESManagerMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.ESManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ESManagerServiceImpl implements ESManagerService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ESManagerMapper esManagerMapper;

    @Autowired
    private SkuFeign skuFeign;


    /**
     * @description: //TODO 创建索引库结构
     * @param: []
     * @return: void
     */
    @Override
    public void createMappingAndIndex() {
        // 创建索引（根据当前索引库的映射实体类来创建）
        elasticsearchTemplate.createIndex(SkuInfo.class);
        // 创建映射（根据当前索引库的映射实体类来创建）
        elasticsearchTemplate.putMapping(SkuInfo.class);
    }


    /**
     * @description: //TODO 导入全部sku集合进入到索引库
     * @param: []
     * @return: void
     */
    @Override
    public void importAll() {
        // 查询sku集合  "all"
        List<Sku> skuList = skuFeign.findSkuListBySpuId("all");

        if (skuList == null || skuList.size() <= 0) {
            throw new RuntimeException("当前没有数据被查询到,无法导入索引库");
        }

        // skuList集合转换为json
        String jsonSkuList = JSON.toJSONString(skuList);
        // 将json转换为skuInfo
        List<SkuInfo> skuInfoList = JSON.parseArray(jsonSkuList, SkuInfo.class);

        for (SkuInfo skuInfo : skuInfoList) {
            // 将规格信息转换为map
            Map specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(specMap);
        }

        // 导入索引库
        esManagerMapper.saveAll(skuInfoList);
    }


    /**
     * @description: //TODO 根据spuId查询skuList,添加到索引库
     * @param: [spuId]
     * @return: void
     */
    @Override
    public void importDataBySpuId(String spuId) {
        // 查询sku集合  "spuId"
        List<Sku> skuList = skuFeign.findSkuListBySpuId(spuId);

        if (skuList == null || skuList.size() <= 0) {
            throw new RuntimeException("当前没有数据被查询到,无法导入索引库");
        }
        // skuList集合转换为json
        String jsonSkuList = JSON.toJSONString(skuList);
        // 将json转换为skuInfo
        List<SkuInfo> skuInfoList = JSON.parseArray(jsonSkuList, SkuInfo.class);

        for (SkuInfo skuInfo : skuInfoList) {
            // 将规格信息进行转换
            Map specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(specMap);
        }

        // 添加索引库
        esManagerMapper.saveAll(skuInfoList);
    }


    /**
     * @description: //TODO 根据spuId删除es索引库中相关的sku数据
     * @param: [spuId]
     * @return: void
     */
    @Override
    public void delDataBySpuId(String spuId) {

        List<Sku> skuList = skuFeign.findSkuListBySpuId(spuId);

        if (skuList == null || skuList.size() <= 0) {
            throw new RuntimeException("当前没有数据被查询到,无法导入索引库");
        }

        for (Sku sku : skuList) {
            esManagerMapper.deleteById(Long.parseLong(sku.getId()));
        }
    }

}
