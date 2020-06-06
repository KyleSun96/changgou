package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * @description: //TODO 商品搜索
     * @param: [searchMap]
     * @return: java.util.Map
     */
    @Override
    public Map search(Map<String, String> searchMap) {

        // 构建查询
        if (searchMap != null) {

            // 2. 第一个参数：构建查询条件封装对象
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            // 4.
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            // 5. 以下开始 商品搜索业务 进行查询条件的构建
            // 5.1 按照关键字模糊查询：name域中
            if (StringUtils.isNotEmpty(searchMap.get("keywords"))) {
                boolQuery.must(QueryBuilders.matchQuery("name", searchMap.get("keywords")).operator(Operator.AND));
            }

            // 5.2 按照品牌进行精确查询
            if (StringUtils.isNotEmpty(searchMap.get("brand"))) {
                boolQuery.filter(QueryBuilders.termQuery("brandName", searchMap.get("brand")));
            }

            // 5.4 按照规格进行过滤查询
            for (String key : searchMap.keySet()) {
                if (key.startsWith("spec_")) {
                    String value = searchMap.get(key).replace("%2B", "+");
                    // 如：spec_网络制式 --> key.substring(5)
                    boolQuery.filter(QueryBuilders.termQuery(("specMap." + key.substring(5) + ".keyword"), value));
                }
            }

            // 5.6 按照价格进行区间过滤查询
            if (StringUtils.isNotEmpty(searchMap.get("price"))) {
                String[] prices = searchMap.get("price").split("-");
                // 0-500 500-1000
                if (prices.length == 2) {
                    boolQuery.filter(QueryBuilders.rangeQuery("price").lte(prices[1]));
                }
                boolQuery.filter(QueryBuilders.rangeQuery("price").gte(prices[0]));
            }

            // 3. 通过boolQuery拼接若干个查询条件
            nativeSearchQueryBuilder.withQuery(boolQuery);

            // 5.3.1 按照品牌进行聚合查询
            String skuBrand = "skuBrand";
            // addAggregation(设置聚合条件)   AggregationBuilders.terms(设置当前聚合查询结果分组的字段列名).field(对索引库哪个域进行查询操作)
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(skuBrand).field("brandName"));

            // 5.5.1 按照规格进行聚合查询
            String skuSpec = "skuSpec";
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(skuSpec).field("spec.keyword"));

            // 5.7.1 开启分页查询
            String pageNum = searchMap.get("pageNum"); // 当前页
            String pageSize = searchMap.get("pageSize"); // 每页显示多少条
            if (StringUtils.isEmpty(pageNum)) {
                pageNum = "1";
            }
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "30";
            }
            // 5.7.2 设置分页 withPageable【当前页（从0开始），每页显示多少条】
            nativeSearchQueryBuilder.withPageable(PageRequest.of(Integer.parseInt(pageNum) - 1, Integer.parseInt(pageSize)));

            // 5.8 按照相关字段进行排序查询
            //  1.当前进行排序的域 2.当前的排序规则(升序ASC，降序DESC)
            if (StringUtils.isNotEmpty(searchMap.get("sortField")) && StringUtils.isNotEmpty(searchMap.get("sortRule"))) {
                if ("ASC".equals(searchMap.get("sortRule"))) {
                    // 升序
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort((searchMap.get("sortField"))).order(SortOrder.ASC));
                } else {
                    // 降序
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort((searchMap.get("sortField"))).order(SortOrder.DESC));
                }
            }

            // 5.9.1 设置高亮域以及高亮的样式
            // 构建高亮域的对象
            HighlightBuilder.Field field = new HighlightBuilder.Field("name")// 高亮域
                    .preTags("<span style='color:red'>")// 高亮样式的前缀
                    .postTags("</span>");// 高亮样式的后缀
            nativeSearchQueryBuilder.withHighlightFields(field);

            /*
                1. 开启查询
                      第一个参数: 条件构建对象
                      第二个参数: 查询结果的封装实体类
                      第三个参数: 查询结果操作对象

                      查询并返回结果：AggregatedPage<SkuInfo> resultInfo =
             */
            AggregatedPage<SkuInfo> resultInfo = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                    // 6. 查询结果操作
                    List<T> list = new ArrayList<>();
                    // 7. 获取查询命中结果数据
                    SearchHits hits = searchResponse.getHits();
                    // 8. 如果有查询结果
                    if (hits != null) {
                        // 9. 遍历获取的每个SearchHit对象hit，即索引库中的每一条记录（doc）
                        for (SearchHit hit : hits) {
                            // 10.1 hit对象 --> json串 --> skuInfo对象
                            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);

                            // 5.9.2 替换高亮数据
                            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                            if (highlightFields != null && highlightFields.size() > 0) {
                                // 获取name高亮域中的第一个内容
                                skuInfo.setName(highlightFields.get("name").getFragments()[0].toString());
                            }

                            // 10.2 将数据放入查询结果数据集合中
                            list.add((T) skuInfo);
                        }
                    }
                    // 11. 返回 参数3 实现方法的返回值【数据集合，分页对象，当前获取的总记录数，？？？】
                    return new AggregatedPageImpl<T>(list, pageable, hits.getTotalHits(), searchResponse.getAggregations());
                }
            });

            // 12. 封装最终的返回结果用于返回前端
            Map<String, Object> resultMap = new HashMap<>();
            // 12.1 总记录数
            resultMap.put("total", resultInfo.getTotalElements());
            // 12.2 总页数
            resultMap.put("totalPages", resultInfo.getTotalPages());
            // 12.3 数据集合
            resultMap.put("rows", resultInfo.getContent());

            // 5.3.2 封装品牌的分组结果
            StringTerms brandTerms = (StringTerms) resultInfo.getAggregation(skuBrand);
            List<String> brandList = brandTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            resultMap.put("brandList", brandList);

            // 5.5.2 封装规格的分组结果
            StringTerms specTerms = (StringTerms) resultInfo.getAggregation(skuSpec);
            List<String> specList = specTerms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            // 规格数据的格式转换
            resultMap.put("specList", this.formatSpec(specList));

            // 5.7.3 当前页
            resultMap.put("pageNum", pageNum);

            return resultMap;
        }

        return null;
    }


    /**
     * @description: //TODO 规格数据的格式转换
     * @param: [specList]
     * @return: java.util.Map<java.lang.String, java.util.Set < java.lang.String>>
     * <p>
     * 原有数据格式(string):
     * [
     * "{'颜色': '黑色', '尺码': '平光防蓝光-无度数电脑手机护目镜'}",
     * "{'颜色': '红色', '尺码': '150度'}",
     * "{'颜色': '黑色', '尺码': '150度'}",
     * "{'颜色': '黑色'}",
     * "{'颜色': '红色', '尺码': '100度'}",
     * "{'颜色': '红色', '尺码': '250度'}",
     * "{'颜色': '红色', '尺码': '350度'}",
     * "{'颜色': '黑色', '尺码': '200度'}",
     * "{'颜色': '黑色', '尺码': '250度'}"
     * ]
     * <p>
     * 需要的数据格式(map):
     * {
     * 颜色:[黑色,红色...],
     * 尺码:[100度,150度...]
     * }
     */
    public Map<String, Set<String>> formatSpec(List<String> specList) {
        // 定义所需的数据map集合
        Map<String, Set<String>> resultMap = new HashMap<>();

        if (specList != null && specList.size() > 0) {
            // 遍历得到单条规格的json字符串：{'颜色': '黑色', '尺码': '150度'}
            for (String specJsonString : specList) {
                // 将json数据转换为map
                Map<String, String> specMap = JSON.parseObject(specJsonString, Map.class);
                // 遍历规格map中的key：specKey --> “颜色”或者“尺码”
                for (String specKey : specMap.keySet()) {
                    // 获取所需map的数据集合
                    Set<String> specSet = resultMap.get(specKey);

                    if (specSet == null) {
                        specSet = new HashSet<String>();
                    }
                    // 将规格的值放入set中：specSet：[黑色,红色...]或者[100度,150度...]
                    specSet.add(specMap.get(specKey));
                    // 将set放入map中
                    resultMap.put(specKey, specSet);
                }
            }
        }
        return resultMap;
    }
}
