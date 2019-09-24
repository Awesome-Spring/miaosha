package com.alibaba.miaosha.service.impl;

import com.alibaba.miaosha.dao.UserDOMapper;
import com.alibaba.miaosha.dao.UserPasswordDOMapper;
import com.alibaba.miaosha.dataobject.UserDO;
import com.alibaba.miaosha.dataobject.UserPasswordDO;
import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.error.EMBusinessError;
import com.alibaba.miaosha.service.UserService;
import com.alibaba.miaosha.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.templateparser.markup.decoupled.IDecoupledTemplateLogicResolver;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel ==null) {
            return;
        }
//       if (StringUtils.isNoneEmpty(userModel.getName())||userModel.getGender()==null||userModel.getAge()==null||StringUtils.isNoneEmpty(userModel.getTelphone())){
//           throw new BusinessException(EMBusinessError.PARAM_ERROR,"参数不合法");
//       }

       //实现UserModel转UserDO
        UserDO  userDO =  convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());

        //实现UserModel转UserPasswordDO
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    public UserDO selectByTelPhone(String telphone) {
        return userDOMapper.selectByelPhone(telphone);
    }

    @Override
    public UserModel validateLogin(String telphone, String password) throws BusinessException {

        UserDO userDO = userDOMapper.selectByelPhone(telphone);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if (userDO == null) {
            throw new BusinessException(EMBusinessError.LOGIN_ERROR, "用户名或密码不正确");
        }

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        boolean exist = StringUtils.equals(userPasswordDO.getEncrptPassword(), password);

        if (exist){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
            return  userModel;
        } else{
            throw new BusinessException(EMBusinessError.LOGIN_ERROR, "用户名或密码不正确");
        }


    }

    private UserDO convertFromModel(UserModel userModel) {
        if (userModel==null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel ,userDO);

        return userDO;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel==null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;

    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if (userDO ==null) {
            return  null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);

        if (userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return  userModel;
    }
}
