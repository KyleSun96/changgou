package com.changgou.web;

import com.changgou.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Program: ChangGou
 * @ClassName: WebOrderApplication
 * @Description:
 * @Author: KyleSun
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.order.feign", "com.changgou.user.feign", "com.changgou.pay.feign"})
public class WebOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebOrderApplication.class, args);
    }

    // feign拦截器：微服务间传递令牌
    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

}
