package com.alibaba.miaosha.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemModel {
    //id
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

    //如果prmodel 不为空，则表示其拥有还未结束的秒杀活动
    private PromoModel  promoModel;
}
