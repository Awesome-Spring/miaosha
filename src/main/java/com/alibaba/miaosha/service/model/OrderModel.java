package com.alibaba.miaosha.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderModel {
    //订单号  时间+自增序列+分库分表号  --> 2019082900000100
    private String id;
    //属于哪个用户的订单
    private Integer userId;

    //商品id
    private Integer itemId;

    //购买商品单价
    private   BigDecimal itemPrice;

    //商品数量
    private Integer amount;

    //订单总价
    private BigDecimal orderPrice;


}
