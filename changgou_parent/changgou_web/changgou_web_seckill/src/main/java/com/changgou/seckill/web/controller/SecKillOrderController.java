package com.changgou.seckill.web.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.seckill.feign.SecKillOrderFeign;
import com.changgou.seckill.web.util.CookieUtil;
import com.changgou.util.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Program: ChangGou
 * @ClassName: SecKillOrderController
 * @Description:
 * @Author: KyleSun
 **/
@RestController
@RequestMapping("/wseckillorder")
public class SecKillOrderController {

    @Autowired
    private SecKillOrderFeign secKillOrderFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: //TODO 秒杀异步下单
     * @param: [time, id]
     * @return: com.changgou.entity.Result
     */
    @RequestMapping("/add")
    public Result add(@RequestParam("time") String time, @RequestParam("id") Long id, @RequestParam("random") String random) {

        // 验证随机数是否正确，实现秒杀下单接口隐藏
        String cookie = this.readCookie();
        String redisRandomCode = (String) redisTemplate.opsForValue().get("randomcode_" + cookie);

        if (StringUtils.isEmpty(redisRandomCode)) {
            return new Result(false, StatusCode.ERROR, "下单失败");
        }
        if (!random.equals(redisRandomCode)) {
            return new Result(false, StatusCode.ERROR, "下单失败");
        }

        return secKillOrderFeign.add(time, id);
    }


    /**
     * @description: //TODO 获取一个随机数
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping("/getToken")
    @ResponseBody
    public String getToken() {

        String randomString = RandomUtil.getRandomString();

        String cookieValue = this.readCookie();

        redisTemplate.opsForValue().set("randomcode_" + cookieValue, randomString, 5, TimeUnit.SECONDS);

        return randomString;
    }


    /**
     * @description: //TODO 从cookie中获取jti
     * @param: []
     * @return: java.lang.String
     */
    private String readCookie() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String jti = CookieUtil.readCookie(request, "uid").get("uid");

        return jti;
    }
}
