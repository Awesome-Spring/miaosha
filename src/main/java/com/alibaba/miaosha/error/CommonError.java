package com.alibaba.miaosha.error;

public interface CommonError {

    int getErrorCode();

    String getMsg();

    CommonError setErrorMsg(String errorMsg);
}
