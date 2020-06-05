package com.changgou.search.service;

import java.util.Map;

public interface SearchService {

    /*
        按照查询条件进行数据查询
            返回值使用 map 类型是因为页面不仅需要返回的查询数据，还有分页的数据存在
            参数使用 map 类型是因为商品搜索不仅只使用关键字查询，可能还需要品牌、规格等参数查询
        因此为了程序后期的可扩展性，我们此时使用 map 类型
     */
    Map search(Map<String, String> searchMap);

}
