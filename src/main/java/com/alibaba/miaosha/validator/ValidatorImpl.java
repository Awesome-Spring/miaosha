package com.alibaba.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

public class ValidatorImpl implements InitializingBean {


    private Validator validator;
    @Override
    public void afterPropertiesSet() throws Exception {
        //将Hibernate validator 通过工厂的初始化方式将其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    /**
     *实现校验方法并返回结果
     */
    public ValidatorResult validate(Object bean){
        ValidatorResult result = new ValidatorResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
       //有错误
        if (constraintViolationSet.size()>0) {
          result.setHasError(true);//设置错误状态
            constraintViolationSet.forEach(e->{
                String errMsg = e.getMessage();//错误详情
                String propertyName = e.getPropertyPath().toString();//拿到错误字段名
                Map<String, String> map = result.getErrorMsgMap();
                map.put(propertyName,errMsg);     //封装进map

            });
        }

        return  result;
    }
}
