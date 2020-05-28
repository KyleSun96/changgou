package com.changgou.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Program: ChangGou
 * @ClassName: GoodsApplication
 * @Description:
 * @Author: KyleSun
 **/
@MapperScan(basePackages = "com.changgou.goods.dao")
@EnableEurekaClient
@SpringBootApplication
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

}
