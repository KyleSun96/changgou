package com.changgou.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Program: ChangGou
 * @ClassName: GatewayApplication
 * @Description:
 * @Author: KyleSun
 **/
@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * @description: //TODO 创建一个唯一标识解析对象，用于识别某个用户，用于限流
     * @return: org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @author: KyleSun swy0907163@163.com
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                // 获取用户ip
                String hostName = exchange.getRequest().getRemoteAddress().getHostName();
                // 将用户ip作为唯一标识
                return Mono.just(hostName);
            }
        };
    }

}
