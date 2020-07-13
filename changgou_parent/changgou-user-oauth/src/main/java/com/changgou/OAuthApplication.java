package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.changgou.auth.dao")
@EnableFeignClients(basePackages = "com.changgou.user.feign")
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }

    /**
     * @description: //TODO 封装web请求，将请求头里的认证信息携带过去
     * @return: org.springframework.web.client.RestTemplate
     * @author: KyleSun swy0907163@163.com
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}