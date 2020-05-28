package com.changgou.goods.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "tb_brand")
public class Brand implements Serializable {

    /*
        @Table和@Id都是JPA注解，@Table用于配置表与实体类的映射关系，@Id用于标识主
        键属性
     */

    @Id
    private Integer id;     //品牌id

    private String name;    //品牌名称

    private String image;   //品牌图片地址

    private String letter;  //品牌的首字母

    private Integer seq;    //排序

}
