package com.alibaba.miaosha.controller.viewobject;

import lombok.Data;
import org.joda.time.DateTime;

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

    //商品活动状态  0 没有  1 待开始  2 已结束
    private Integer promoStatus;

    //秒杀活动价格
    private BigDecimal promoPrice;

    //秒杀活动id
    private  Integer promoId;

    //秒杀活动开始时间
    private DateTime satrtTime;
}
