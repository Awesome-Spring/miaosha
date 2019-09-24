package com.alibaba.miaosha.dao;

import com.alibaba.miaosha.dataobject.UserPasswordDO;
import com.alibaba.miaosha.dataobject.UserPasswordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPasswordDOMapper {
    int countByExample(UserPasswordDOExample example);

    int deleteByExample(UserPasswordDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    List<UserPasswordDO> selectByExample(UserPasswordDOExample example);

    UserPasswordDO selectByPrimaryKey(Integer id);

    UserPasswordDO selectByUserId(Integer userId);

    int updateByExampleSelective(@Param("record") UserPasswordDO record, @Param("example") UserPasswordDOExample example);

    int updateByExample(@Param("record") UserPasswordDO record, @Param("example") UserPasswordDOExample example);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);
}