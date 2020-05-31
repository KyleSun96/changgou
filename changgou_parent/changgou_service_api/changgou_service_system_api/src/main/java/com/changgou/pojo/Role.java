package com.changgou.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * role实体类
 */
@Getter
@Setter
@Table(name = "tb_role")
public class Role implements Serializable {

    @Id
    private Integer id;		//ID

    private String name;	//角色名称

}
