package com.changgou.goods.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * template实体类
 */
@Getter
@Setter
@Table(name = "tb_template")
public class Template implements Serializable {

    @Id
    private Integer id;        	//ID

    private String name;        //模板名称

    private Integer specNum;    //规格数量

    private Integer paraNum;    //参数数量

}
