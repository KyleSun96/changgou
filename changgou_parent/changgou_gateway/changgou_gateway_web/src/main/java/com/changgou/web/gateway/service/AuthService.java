package com.changgou.web.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

/**
 * @Program: ChangGou
 * @ClassName: AuthService
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class AuthService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @description: //TODO 从cookie中获取Jti
     * @param: [request]
     * @return: java.lang.String
     */
    public String getJtiFromCookie(ServerHttpRequest request) {

        HttpCookie httpCookie = request.getCookies().getFirst("uid");
        if (httpCookie != null){
            String jti = httpCookie.getValue();
            return jti;
        }
        return null;
    }


    /**
     * @description: //TODO 从redis中获取jwt的值
     * @param: [jti]
     * @return: java.lang.String
     */
    public String getJwtFromRedis(String jti) {
        String jwt = stringRedisTemplate.boundValueOps(jti).get();
        return jwt;
    }
}
