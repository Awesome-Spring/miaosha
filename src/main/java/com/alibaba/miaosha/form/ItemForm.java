package com.alibaba.miaosha.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItemForm {



    //商品标题
    @NotBlank(message = "请输入商品名称")
    private  String title;

    //商品价格
    @NotNull(message = "请输入商品价格")
    @Min(value = 0,message = "商品价格应大于0")
    private BigDecimal price;

    //商品描述
    @NotBlank(message = "请输入商品描述信息")
    private  String decription;

    //商品销量
    @NotNull(message = "请输入商品销量")
    @Min(value = 0,message = "商品销量需大于0")
    private Integer sales;

    //商品图片
    @NotBlank(message = "请输入商品图片信息")
    private String imgUrl;
}
