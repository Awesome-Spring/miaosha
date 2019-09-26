package com.alibaba.miaosha.service.impl;

import com.alibaba.miaosha.dao.ItemDOMapper;
import com.alibaba.miaosha.dao.ItemStockDOMapper;
import com.alibaba.miaosha.dataobject.ItemDO;
import com.alibaba.miaosha.dataobject.ItemStockDO;
import com.alibaba.miaosha.service.ItermService;
import com.alibaba.miaosha.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItermServiceImpl implements ItermService {

    @Autowired
   private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;



    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) {
        //转化尾DO
        ItemDO itemDO = convertFormModel(itemModel);

        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());//拿到数据库中自增id封装进model

        ItemStockDO itemStockDO = convertStockFormModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItems() {
        //查询出所有ItemDO
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        //封装成ItemModel
        List<ItemModel> itemModels = itemDOList.stream().map(itemDO-> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
            return  itemModel;
        }).collect(Collectors.toList());

        return itemModels;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO= itemDOMapper.selectByPrimaryKey(id);
        if (itemDO ==null) {
            return  null;
        }

        //操作拿到库存数量,封装进Model
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
       if (itemStockDO==null) {
           return  null;
       }

      ItemModel itemModel =  convertModelFromDataObject(itemDO,itemStockDO);
       itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }


    private ItemDO convertFormModel(ItemModel itemModel){

        if (itemModel == null) {
            return  null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return  itemDO;
    }

    private ItemStockDO convertStockFormModel(ItemModel itemModel){

        if (itemModel == null) {
            return  null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());

        return  itemStockDO;
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return  itemModel;
    }
}
