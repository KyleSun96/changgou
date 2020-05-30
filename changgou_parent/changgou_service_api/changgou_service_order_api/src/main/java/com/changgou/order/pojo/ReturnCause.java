package com.changgou.order.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * returnCause实体类
 */
@Getter
@Setter
@Table(name = "tb_return_cause")
public class ReturnCause implements Serializable {

    @Id
    private Integer id;		//ID

    private String cause;	//原因

    private Integer seq;	//排序

    private String status;	//是否启用

}
