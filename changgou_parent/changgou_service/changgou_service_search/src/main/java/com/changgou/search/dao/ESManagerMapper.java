package com.changgou.search.dao;

import com.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// 【操作的实体类，实体类中主键的数据类型】
public interface ESManagerMapper extends ElasticsearchRepository<SkuInfo, Long> {
}
