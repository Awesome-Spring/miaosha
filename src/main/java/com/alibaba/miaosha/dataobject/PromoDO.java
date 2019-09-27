package com.alibaba.miaosha.dataobject;

import lombok.Data;

import java.util.Date;

@Data
public class PromoDO {
    private Integer id;

    private String promoName;

    private Date startTime;

    private Date endTime;

    private Integer itemId;

    private Double promoPrice;


}