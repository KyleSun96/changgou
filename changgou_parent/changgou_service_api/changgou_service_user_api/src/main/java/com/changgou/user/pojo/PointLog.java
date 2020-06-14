package com.changgou.user.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * @Program: ChangGou
 * @ClassName: PointLog
 * @Description: 积分日志实体类
 * @Author: KyleSun
 **/
@Getter
@Setter
@Table(name = "tb_point_log")
public class PointLog {

    private String orderId;

    private String userId;

    private Integer point;

}
