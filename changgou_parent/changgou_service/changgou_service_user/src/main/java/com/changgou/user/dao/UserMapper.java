package com.changgou.user.dao;

import com.changgou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {


    /**
     * @description: //TODO 添加用户积分
     * @param: [username, point]
     * @return: int
     */
    @Update("UPDATE tb_user SET points = points + #{point} WHERE username=#{username} ")
    int addUserPoints(@Param("username") String username, @Param("point") Integer point);

}
