package com.alibaba.miaosha.service;

import com.alibaba.miaosha.service.model.PromoModel;

public interface PromoService {

    //获取正在进行的或即将进行的秒杀活动商品
    PromoModel getPromoByItemId(Integer itemId);
}
