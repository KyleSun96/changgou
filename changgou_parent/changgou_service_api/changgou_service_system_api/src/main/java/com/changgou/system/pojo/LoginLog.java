package com.changgou.system.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * loginLog实体类
 */
@Getter
@Setter
@Table(name = "tb_login_log")
public class LoginLog implements Serializable {

    @Id
    private Integer id;					//id

    private String loginName;			//login_name

    private String ip;					//ip

    private String browserName;			//browser_name

    private String location;			//地区

    private java.util.Date loginTime;	//登录时间

}
