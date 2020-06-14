package com.changgou.order.service;

import com.changgou.order.pojo.Task;

/**
 * @Program: ChangGou
 * @InterfaceName: TaskService
 * @Description:
 * @Author: KyleSun
 **/
public interface TaskService {

    // 删除原有的任务数据，并向历史任务表中添加记录
    void delTask(Task task);

}
