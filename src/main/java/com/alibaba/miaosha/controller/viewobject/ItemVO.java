package com.alibaba.miaosha.controller.viewobject;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVO {
    //商品id
    private  Integer id;
    //商品标题
    private  String title;
    //商品价格
    private BigDecimal price;
    //商品描述
    private  String decription;
    //商品销量
    private Integer sales;
    //商品库存
    private Integer stock;
    //商品图片
    private String imgUrl;
}
