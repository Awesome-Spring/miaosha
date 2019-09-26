package com.alibaba.miaosha.dataobject;

import lombok.Data;

@Data
public class ItemDO {

    private Integer id;

    private String title;

    private Double price;

    private String decription;

    private Integer sales;

    private String imgUrl;


}