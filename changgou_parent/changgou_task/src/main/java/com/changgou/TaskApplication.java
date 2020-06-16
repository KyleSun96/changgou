package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Program: ChangGou
 * @ClassName: TaskApplication
 * @Description: SpringTask 实现定时任务
 * @Author: KyleSun
 **/
@SpringBootApplication
@EnableScheduling   // 开启定时任务
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

}
