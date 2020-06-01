package com.changgou.goods.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Program: ChangGou
 * @ClassName: CategoryBrand
 * @Description: 品牌与分类关联实体类
 * @Author: KyleSun
 **/
@Getter
@Setter
@Table(name = "tb_category_brand")
public class CategoryBrand implements Serializable {

    @Id
    private Integer categoryId;     // 分类ID

    @Id
    private Integer brandId;        // 品牌ID

}
