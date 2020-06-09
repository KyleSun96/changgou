package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Program: ChangGou
 * @InterfaceName: UserFeign
 * @Description: 用户服务远程调用接口
 * @Author: KyleSun
 **/
@FeignClient(name = "user")     //声明调用的服务
public interface UserFeign {

    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username") String username);

}
