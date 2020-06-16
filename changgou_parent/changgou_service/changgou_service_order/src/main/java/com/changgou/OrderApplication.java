package com.changgou;

import com.changgou.interceptor.FeignInterceptor;
import com.changgou.order.config.TokenDecode;
import com.changgou.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling   // 开启定时任务
@MapperScan(basePackages = {"com.changgou.order.dao"})
@EnableFeignClients(basePackages = {"com.changgou.goods.feign", "com.changgou.user.feign", "com.changgou.pay.feign"})
// 添加feign接口扫描
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }

    // 解析令牌，动态获取用户信息
    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

    // 设置订单ID：雪花算法
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

    // 订单服务调用商品服务，基于feign调用扣减库存方法，需要传递令牌验证身份
    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

}
