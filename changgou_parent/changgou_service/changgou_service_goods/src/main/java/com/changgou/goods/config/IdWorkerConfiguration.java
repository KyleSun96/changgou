package com.changgou.goods.config;

import com.changgou.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Program: ChangGou
 * @ClassName: IdWorkerConfiguration
 * @Description: IdWorker 配置类
 * @Author: KyleSun
 **/
@Configuration
public class IdWorkerConfiguration {

    @Value("${workerId}")
    private int workerId;

    @Value("${datacenterId}")
    private int datacenterId;

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(workerId, datacenterId);
    }

}
