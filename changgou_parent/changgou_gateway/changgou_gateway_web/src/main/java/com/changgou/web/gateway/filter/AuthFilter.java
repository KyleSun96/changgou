package com.changgou.web.gateway.filter;

import com.changgou.web.gateway.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Program: ChangGou
 * @ClassName: AuthFilter
 * @Description:
 * @Author: KyleSun
 **/
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    // 设置未登录时的登录页面跳转路径
    private static final String LOGIN_URL = "http://localhost:8001/api/oauth/toLogin";

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 1.判断当前请求路径是否为登录请求，如果是，则直接放行
        String path = request.getURI().getPath();
        if ("/oauth/login".equals(path) || !UrlFilter.hasAuthorize(path)) {
            // 直接放行
            return chain.filter(exchange);
        }

        // 2.从cookie中获取jti的值，如果该值不存在，拒绝本次访问
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)) {
            // 拒绝访问
            /*response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();*/
            // 跳转到登录界面
            return this.toLoginPage(LOGIN_URL + "?FROM=" + request.getURI(), exchange);

        }

        // 3.从redis中获取jwt的值，如果该值不存在，拒绝本次访问
        String jwt = authService.getJwtFromRedis(jti);
        if (StringUtils.isEmpty(jwt)) {
            // 拒绝访问
            /*response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();*/
            // 跳转到登录界面
            return this.toLoginPage(LOGIN_URL + "?FROM=" + request.getURI(), exchange);
        }

        // 4.对当前的请求对象进行增强，让它会携带令牌的信息
        request.mutate().header("Authorization", "Bearer " + jwt);
        return chain.filter(exchange);
    }


    /**
     * @description: //TODO 跳转到登录界面
     * @param: [loginUrl, exchange]
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     */
    private Mono<Void> toLoginPage(String loginUrl, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        // 设置状态码302 跳转
        response.setStatusCode(HttpStatus.SEE_OTHER);
        // header中添加跳转地址
        response.getHeaders().set("Location", loginUrl);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
