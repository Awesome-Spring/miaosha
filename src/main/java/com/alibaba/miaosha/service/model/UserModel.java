package com.alibaba.miaosha.service.model;

import lombok.Data;

@Data
public class UserModel {

    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private String thirdPartyId;

    private  String encrptPassword;
}
