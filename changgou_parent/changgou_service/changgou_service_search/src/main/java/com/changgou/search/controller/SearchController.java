package com.changgou.search.controller;

import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * @description: //TODO 商品搜索
     * @param: [searchMap]
     * @return: java.util.Map
     */
    @GetMapping
    public Map search(@RequestParam Map<String, String> searchMap) {
        /*
            特殊符号处理
                商品的规格中可能含有特殊符号，如 = + 等，无法在URL路径中直接传递（get请求）
                因此需要将 '+' 转为 '%2B'
         */
        this.handleSearchMap(searchMap);
        return searchService.search(searchMap);
    }


    /**
     * @description: //TODO 特殊符号处理
     * @param: [searchMap]
     * @return: void
     */
    private void handleSearchMap(Map<String, String> searchMap) {
        // 获取map中的每一个键值对
        Set<Map.Entry<String, String>> entries = searchMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().startsWith("spec_")) {
                searchMap.put(entry.getKey(), entry.getValue().replace("+", "%2B"));
            }
        }
    }
}
