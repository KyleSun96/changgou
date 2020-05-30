package com.changgou.user.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * provinces实体类
 */
@Getter
@Setter
@Table(name = "tb_provinces")
public class Provinces implements Serializable {

    @Id
    private String provinceid;//省份ID

    private String province;//省份名称

}
