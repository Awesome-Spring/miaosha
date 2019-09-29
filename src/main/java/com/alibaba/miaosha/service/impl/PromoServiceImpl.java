package com.alibaba.miaosha.service.impl;

import com.alibaba.miaosha.dao.PromoDOMapper;
import com.alibaba.miaosha.dataobject.PromoDO;
import com.alibaba.miaosha.service.PromoService;
import com.alibaba.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获得对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        //DataObject  -->  Model
        PromoModel promoModel = convertFromDataObject(promoDO);

        if (promoModel ==null) { //没有活动
            return  null;
        }
        //判断当前商品秒杀活动状态
        DateTime now = DateTime.now();
        if ( promoModel.getStartTime().isAfter(now)){ //秒杀活动时间在当前时间之后，还未开始)
            promoModel.setStatus(1);
        }else  if (promoModel.getEndTime().isBefore(now)) {//秒杀活动结束时间在当前时间之前，已经结束)
            promoModel.setStatus(3);
        } else if (promoModel.getStartTime().isBefore(now)&&promoModel.getEndTime().isAfter(now)){ //正在进行
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO) {

        if (promoDO == null) {  //没有活动
            return  null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoPrice(new BigDecimal(promoDO.getPromoPrice()));
        promoModel.setStartTime(new DateTime(promoDO.getStartTime()));
        promoModel.setEndTime(new DateTime(promoDO.getEndTime()));

        return  promoModel;
    }
}
