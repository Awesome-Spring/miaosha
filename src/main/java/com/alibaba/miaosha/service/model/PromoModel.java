package com.alibaba.miaosha.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * 秒杀活动模型
 */
@Data
public class PromoModel {


    private Integer id;

    //秒杀活动状态 1 未开始  2 进行中  3 已结束
    private Integer status;

    //秒杀活动名称
    private  String promoName;

    //秒杀开始时间
    private DateTime startTime;

    //秒杀结束时间
    private DateTime endTime;

    //秒杀活动商品id
    private Integer itemId;

    //秒杀活动价格
    private BigDecimal promoPrice;



}


