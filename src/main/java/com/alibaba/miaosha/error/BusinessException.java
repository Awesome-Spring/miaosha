package com.alibaba.miaosha.error;

//包装器业务异常实现
public class BusinessException extends  Exception implements  CommonError {

    private CommonError commonError;



    //直接接受EMBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    //接受自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(errMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getMsg() {
        return this.commonError.getMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        return this.commonError.setErrorMsg(errorMsg);
    }
}
