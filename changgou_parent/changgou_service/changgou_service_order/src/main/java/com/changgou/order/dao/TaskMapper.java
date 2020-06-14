package com.changgou.order.dao;

import com.changgou.order.pojo.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @Program: ChangGou
 * @InterfaceName: TaskMapper
 * @Description:
 * @Author: KyleSun
 **/
public interface TaskMapper extends Mapper<Task> {

    /**
     * @description: //TODO 获取小于系统当前时间的数据
     * @param: [currentTime]
     * @return: java.util.List<com.changgou.order.pojo.Task>
     * <p>
     * 添加映射：column数据库字段名，property实体类属性名
     */
    @Select("SELECT * FROM `tb_task` WHERE update_time < #{currentTime}")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_time", property = "deleteTime"),
            @Result(column = "task_type", property = "taskType"),
            @Result(column = "mq_exchange", property = "mqExchange"),
            @Result(column = "mq_routingkey", property = "mqRoutingkey"),
            @Result(column = "request_body", property = "requestBody"),
            @Result(column = "status", property = "status"),
            @Result(column = "errormsg", property = "errormsg")
    })
    List<Task> findTaskLessThanCurrentTime(@Param("currentTime") Date currentTime);

}
