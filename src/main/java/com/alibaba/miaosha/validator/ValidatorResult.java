package com.alibaba.miaosha.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class ValidatorResult {

    private boolean hasError =false;

    Map<String,String> errorMsgMap = new HashMap<>();

    public String getErrorMsg (){
       return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
