package com.alibaba.miaosha.service;

import com.alibaba.miaosha.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel);

    //商品列表
    List<ItemModel> listItems();

    //商品详情
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean  decreateStock(Integer itemId, Integer amount);

    //增加销量
    boolean increateSales(Integer itemId,Integer amount);
}
