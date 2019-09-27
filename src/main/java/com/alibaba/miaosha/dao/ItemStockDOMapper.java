package com.alibaba.miaosha.dao;

import com.alibaba.miaosha.dataobject.ItemStockDO;
import com.alibaba.miaosha.dataobject.ItemStockDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {
    int countByExample(ItemStockDOExample example);

    int deleteByExample(ItemStockDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    List<ItemStockDO> selectByExample(ItemStockDOExample example);

    ItemStockDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    int updateByExample(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    ItemStockDO selectByItemId(Integer id);

    int decreaseStock(@Param("itemId")Integer itemId,@Param("amount") Integer amount);


}