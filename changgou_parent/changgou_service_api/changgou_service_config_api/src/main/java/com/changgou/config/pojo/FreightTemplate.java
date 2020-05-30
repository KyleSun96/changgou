package com.changgou.config.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * freightTemplate实体类
 */
@Getter
@Setter
@Table(name = "tb_freight_template")
public class FreightTemplate implements Serializable {

    @Id
    private Integer id;    	//ID

    private String name;    //模板名称

    private String type;    //计费方式

}
