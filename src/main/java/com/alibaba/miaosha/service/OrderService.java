package com.alibaba.miaosha.service;

import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.service.model.OrderModel;

public interface OrderService {

    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
