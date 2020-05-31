package com.changgou.system.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * admin实体类
 */
@Getter
@Setter
@Table(name = "tb_admin")
public class Admin implements Serializable {

    @Id
    private Integer id;			//id

    private String loginName;	//用户名

    private String password;	//密码

    private String status;		//状态

}
