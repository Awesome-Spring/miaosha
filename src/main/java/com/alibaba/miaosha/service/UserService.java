package com.alibaba.miaosha.service;

import com.alibaba.miaosha.dataobject.UserDO;
import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);

   void register(UserModel userModel) throws BusinessException;

    UserDO selectByTelPhone(String telphone);
}
