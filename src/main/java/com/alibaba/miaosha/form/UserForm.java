package com.alibaba.miaosha.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class UserForm {

    //@NotEmpty 用在集合类上面
    //@NotBlank 用在String上面
    //@NotNull  用在基本类型上


    @NotBlank(message = "手机号不能为空")
   @Size(min = 11,max = 11,message = "手机号格式不正确")
    private String telphone;

    @NotBlank(message = "请输入验证码")
    @Size(min = 6, message = "验证码不正确")
    private String otpCode;

    @NotBlank(message = "请输入合法昵称")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Size(min=6,max=12,message="密码长度必须在6~12位")
    private  String encrptPassword;

    @NotNull
    private Integer gender;

    @NotNull(message = "请输入年龄")
    @Max(value = 150 ,message = "年龄输入有误")
    @Min(value = 0 ,message = "年龄输入有误")
    private Integer age;

}
