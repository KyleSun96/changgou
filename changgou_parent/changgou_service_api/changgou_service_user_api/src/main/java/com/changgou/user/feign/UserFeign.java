package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: ChangGou
 * @InterfaceName: UserFeign
 * @Description: 用户服务远程调用接口
 * @Author: KyleSun
 **/
@FeignClient(name = "user")     //声明调用的服务
public interface UserFeign {


    /**
     * @description: //TODO 根据ID查询数获取user用户
     * @param: [username]
     * @return: com.changgou.user.pojo.User
     */
    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username") String username);


    /**
     * @description: //TODO 添加用户积分
     * @param: [point]
     * @return: com.changgou.entity.Result
     */
    @GetMapping("/user/point/add")
    public Result addUserPoints(@RequestParam("point") Integer point);


}
