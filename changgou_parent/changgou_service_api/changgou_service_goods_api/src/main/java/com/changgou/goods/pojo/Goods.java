package com.changgou.goods.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: ChangGou
 * @ClassName: Goods
 * @Description: 自定义组合实体类
 * @Author: KyleSun
 **/
@Getter
@Setter
public class Goods implements Serializable {

    private Spu spu;

    private List<Sku> skuList;

}
