package com.alibaba.miaosha.response;

import lombok.Data;

@Data
public class CommonReturnType {

    //表明对应请求的返回处理结果，"success" 或者"fail"
    private String status;

    //若status为success，则data内返回前端所需要处理的json数据
    //若status为fail，则data内使用同用的错误码格式
    private Object data;
    
    public static  CommonReturnType create(Object result){
        return  CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String status) {

        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setData(result);
        return  commonReturnType;
    }
}
