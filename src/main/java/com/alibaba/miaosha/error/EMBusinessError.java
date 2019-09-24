package com.alibaba.miaosha.error;

import lombok.Data;
import lombok.Getter;

@Getter
public enum EMBusinessError implements  CommonError {

    //同用错误类型 100001
    PARMAERER_VALIDATION_ERROR(100001,"参数不合法"),
    UNKNOWN_ERROR(100002,"未知错误"),

    //20000开头表示用户相关错误定义
    USER_NOT_EXIT(20001,"用户不存在"),
    PARAM_ERROR(20002,"参数错误"),
    TELPHONE_EXIST(20003,"手机号已注册")
    ;


    private  int errorCode;

    private  String errorMsg;

    EMBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMsg() {
        return errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg =errorMsg;
        return this;
    }
}
