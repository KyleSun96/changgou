package com.changgou.system.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * @Program: ChangGou
 * @ClassName: IPFilter
 * @Description: 获取客户端的访问ip
 * @Author: KyleSun
 **/
@Component
public class IPFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("经过了第一个过滤器");
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        System.out.println("ip:" + remoteAddress.getHostName());
        // 放行
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        // 过滤器的执行优先级,返回值越小,执行优先级越高
        return 1;
    }

}
