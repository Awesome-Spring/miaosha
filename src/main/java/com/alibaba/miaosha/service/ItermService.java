package com.alibaba.miaosha.service;

import com.alibaba.miaosha.service.model.ItemModel;

import java.util.List;

public interface ItermService {

    //创建商品
    ItemModel createItem(ItemModel itemModel);

    //商品列表
    List<ItemModel> listItems();

    //商品详情
    ItemModel getItemById(Integer id);
}
