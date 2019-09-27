package com.alibaba.miaosha.error.handler;

import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.error.EMBusinessError;
import com.alibaba.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class MyExceptionHandler {

   @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handler(HttpServletRequest request, Exception ex) {
        Map<String, Object> reponseData = new HashMap<>();

        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            reponseData.put("errCode", businessException.getErrorCode());
            reponseData.put("errMsg", businessException.getMsg());
        }else {
            reponseData.put("errCode",  EMBusinessError.UNKNOWN_ERROR.getErrorCode());
            reponseData.put("errMsg", EMBusinessError.UNKNOWN_ERROR.getMsg());

        }
        return CommonReturnType.create(reponseData, "fail");


    }
}
