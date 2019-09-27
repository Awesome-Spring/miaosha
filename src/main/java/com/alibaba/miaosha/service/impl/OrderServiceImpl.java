package com.alibaba.miaosha.service.impl;

import com.alibaba.miaosha.dao.OrderDOMapper;
import com.alibaba.miaosha.dao.SequenceDOMapper;
import com.alibaba.miaosha.dataobject.OrderDO;
import com.alibaba.miaosha.dataobject.SequenceDO;
import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.error.EMBusinessError;
import com.alibaba.miaosha.service.ItemService;
import com.alibaba.miaosha.service.OrderService;
import com.alibaba.miaosha.service.UserService;
import com.alibaba.miaosha.service.model.ItemModel;
import com.alibaba.miaosha.service.model.OrderModel;
import com.alibaba.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;
    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    SequenceDOMapper sequenceDOMapper;

    /**
     * 下单
     *
     * @param userId 下单用户
     * @param itemId 下单商品
     * @param amount 商品数量
     */
    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1.校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EMBusinessError.ITEM_NOT_EXIST, "该商品信息有误");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EMBusinessError.USER_NOT_EXIT, "用户信息有误");
        }
        if (amount < 0 || amount > 99) {
            throw new BusinessException(EMBusinessError.PARAM_ERROR, "商品数量有误");
        }

        //2.落单减库存
        boolean success = itemService.decreateStock(itemId, amount);
        if (!success) {
            throw new BusinessException(EMBusinessError.STOCK_NOT_ENOUGH, "库存不足");
        }

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId); //订单所属用户
        orderModel.setItemId(itemId); //商品号
        orderModel.setAmount(amount);//商品数量
        orderModel.setItemPrice(itemModel.getPrice());//商品单价
        orderModel.setOrderPrice(itemModel.getPrice().multiply(BigDecimal.valueOf(amount)));

        //设置唯一订单号
        orderModel.setId(generateOrderNumber());
        //封装OrderDO对象，保存数据库
        OrderDO orderDO = convertFromOrderModel(orderModel);

        orderDOMapper.insert(orderDO);

        //4.返回前端

        return orderModel;
    }

    /**
     * 生成订单号
     * 订单号生成规则：年+月+日+自增序列+分库分表位
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)//设置事务传播级别（保证订单号唯一性）
    private String generateOrderNumber() {
        StringBuilder sb = new StringBuilder();
        //获取年月日信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replaceAll("-", "");
        sb.append(nowDate);

        //自增序列（从数据库中拿到自增序列）
        int senquence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceBuName("order_info"); //拿到当前项目所对应的自增序列对象
        senquence =sequenceDO.getCurrentValue();//拿到当前的自增值

        //更新当前自增值，保证下次拿到的不重复
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+ sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        //拼接成6为自增序列（不足补零）
        String sequenceStr = String.valueOf(senquence);
        sb.append(sequenceStr);
        for (int i = 0; i <6-sequenceStr.length(); i++) {
        sb.append("0");
        }
        sb.append(senquence);

        //分库分表位（00-99）
        sb.append("00");
        return sb.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;
    }

}
