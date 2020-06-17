package com.changgou.seckill.web.controller;

import com.changgou.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Program: ChangGou
 * @ClassName: SecKillGoodsController
 * @Description:
 * @Author: KyleSun
 **/
@Controller
@RequestMapping("/wseckillgoods")
public class SecKillGoodsController {


    /**
     * @description: //TODO 跳转秒杀首页
     * @param: []
     * @return: java.lang.String
     */
    @RequestMapping("/toIndex")
    public String toIndex() {
        return "seckill-index";
    }

    /**
     * @description: //TODO 获取秒杀时间段集合信息
     * @param: []
     * @return: java.util.List<java.lang.String>
     */
    @ResponseBody
    @RequestMapping("/timeMenus")
    public List<String> dateMenus() {

        List<String> result = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间段相关的信息集合
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            String format = simpleDateFormat.format(dateMenu);
            result.add(format);
        }
        return result;
    }
}
