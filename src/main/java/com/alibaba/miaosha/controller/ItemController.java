package com.alibaba.miaosha.controller;

import com.alibaba.miaosha.controller.viewobject.ItemVO;
import com.alibaba.miaosha.form.ItemForm;
import com.alibaba.miaosha.response.CommonReturnType;
import com.alibaba.miaosha.service.ItemService;
import com.alibaba.miaosha.service.model.ItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
@Slf4j
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/get")
    public CommonReturnType getItem(@RequestParam("id")Integer id) {
        ItemModel itemModel = itemService.getItemById(id);

        ItemVO itemVO = convertVOFromModel(itemModel);

        return CommonReturnType.create(itemVO);
    }

    @GetMapping("/list")
    public CommonReturnType getAllItems() {

        List<ItemModel> itemModelList = itemService.listItems();

        /**
         * 使用 stream 方式将list内的ItemModel 转化为ItermVO
         */
        List<ItemVO>  itemVOList = itemModelList.stream().map(e->{
            ItemVO itemVO = convertVOFromModel(e);
            return  itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);
    }

    @PostMapping("/create")
    public CommonReturnType createItem(@Validated ItemForm itemForm, BindingResult result) {

        //封装service请求创建商品
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemForm, itemModel);
        ItemModel itemReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemReturn);//返回前端的对象

        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if (itemModel.getPromoModel() != null) { //有秒杀活动，封装相关秒杀属性
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus()); //秒杀活动状态
            itemVO.setPromoId(itemModel.getPromoModel().getId()); //秒杀活动Id
            itemVO.setSatrtTime(itemModel.getPromoModel().getStartTime());//秒杀活动开始时间
            itemVO.setPrice(itemModel.getPromoModel().getPromoPrice());//秒杀活动开始时间

        }else { //没有秒杀活动
            itemVO.setPromoStatus(0);
        }

        return  itemVO;
    }
}
