package com.alibaba.miaosha.controller;

import com.alibaba.miaosha.dao.ItemDOMapper;
import com.alibaba.miaosha.dao.OrderDOMapper;
import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.error.EMBusinessError;
import com.alibaba.miaosha.response.CommonReturnType;
import com.alibaba.miaosha.service.ItemService;
import com.alibaba.miaosha.service.OrderService;
import com.alibaba.miaosha.service.model.OrderModel;
import com.alibaba.miaosha.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    HttpServletRequest httpServletRequest;



    @PostMapping("/create")
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId, @RequestParam(name="amount")Integer  amount) throws BusinessException {
        if (itemId==null||amount==null){
            throw  new BusinessException(EMBusinessError.PARAM_ERROR,"请选择要购买的商品以及数量");
        }

        //判断当前用户是否登录
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        if (userModel==null||!isLogin) {
            throw  new BusinessException(EMBusinessError.USER_NOT_LOGIN,"用户未登录，下单失败");
        }
        OrderModel order = orderService.createOrder(userModel.getId(), itemId, amount);

        //下单成功，销量增加
        if (order!=null) {
            itemService.increateSales(itemId,amount);
        }

        return  CommonReturnType.create(null);

    }
}
