package com.changgou.search.controller;

import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * @description: //TODO 商品搜索 + 页面跳转
     * @param: [searchMap, model]【前端传递过来的查询条件，跳转去search页面所携带的查询结果数据】
     * @return: java.lang.String
     */
    @GetMapping("/list")
    public String list(@RequestParam Map<String, String> searchMap, Model model) {

        // 特殊符号处理
        this.handleSearchMap(searchMap);

        // 获取查询结果
        Map resultMap = this.search(searchMap);

        // 跳转页面时所需携带的数据
        model.addAttribute("result", resultMap);
        model.addAttribute("searchMap", searchMap);

        // 拼接URL：spliceURL
        StringBuilder url = new StringBuilder("/search/list");
        if (searchMap != null && searchMap.size() > 0) {
            // searchMap含有查询条件时
            url.append("?");
            for (String paramKey : searchMap.keySet()) {
                if (!"sortRule".equals(paramKey) && !"sortField".equals(paramKey) && !"pageNum".equals(paramKey)) {
                    url.append(paramKey).append("=").append(searchMap.get(paramKey)).append("&");
                }
                // http://localhost:9009/search/list?keywords=手机&spec_网络制式=4G&
                String urlString = url.toString();
                // 去除路径上的最后一个 &
                urlString = urlString.substring(0, urlString.length() - 1);
                model.addAttribute("url", urlString);
            }
        } else {
            model.addAttribute("url", url);
        }
        return "search";
    }


    /**
     * @description: //TODO 商品搜索
     * @param: [searchMap]
     * @return: java.util.Map
     */
    @GetMapping
    @ResponseBody
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
