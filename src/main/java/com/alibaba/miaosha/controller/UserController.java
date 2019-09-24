package com.alibaba.miaosha.controller;

import com.alibaba.miaosha.controller.viewobject.UserVO;
import com.alibaba.miaosha.dao.UserDOMapper;
import com.alibaba.miaosha.dataobject.UserDO;
import com.alibaba.miaosha.error.BusinessException;
import com.alibaba.miaosha.error.EMBusinessError;
import com.alibaba.miaosha.form.UserForm;
import com.alibaba.miaosha.response.CommonReturnType;
import com.alibaba.miaosha.service.UserService;
import com.alibaba.miaosha.service.model.UserModel;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@Slf4j
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws Exception {

        UserModel userModel = userService.getUserById(id);
        if (userModel == null) {
            throw new BusinessException(EMBusinessError.USER_NOT_EXIT);
        }
        UserVO userVO = convertFromModel(userModel);

        return CommonReturnType.create(userVO);

    }

    private UserVO convertFromModel(UserModel userModel) {

        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

    /**
     * 用户获取otp短信接口
     */
    @PostMapping(value = "/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam("telphone") String telphone, HttpSession session) {
        //1.获取用户OTP
        //随机生成6位数OTP
        String otpCode = String.valueOf(new Random().nextInt(999999) + 100000);
        //2.将OTP验证码同对应用户手机号关联
        session.setAttribute(telphone, otpCode);

        //3. 将OTP验证码通过短信通道发送给用户
        //TODO
        log.info("telPhone={}&otpCode={}", telphone, otpCode);
        //System.out.println("telphone:"+telphone);

        return CommonReturnType.create(null);
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonReturnType regist(@Validated UserForm userForm, BindingResult result,HttpSession session) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        if (result.hasErrors()) {
            log.error("【用户注册】+参数不合法，UserForm={}", userForm);
            throw new BusinessException(EMBusinessError.PARAM_ERROR, result.getFieldError().getDefaultMessage());
        }
        String telphone = userForm.getTelphone();
        UserDO userDO = userService.selectByTelPhone(telphone);
        if (userDO ==null) {
            //验证手机号和验证码相匹配
            String otpCode = (String) session.getAttribute(userForm.getTelphone());
            if (!com.alibaba.druid.util.StringUtils.equals(otpCode, userForm.getOtpCode())) {
                throw new BusinessException(EMBusinessError.PARAM_ERROR, "验证码不正确");
            }

            UserModel userModel = convert2Model(userForm);

            //用户注册
            userService.register(userModel);
        }else {
            throw new BusinessException(EMBusinessError.TELPHONE_EXIST, "该手机号已注册");
        }



        return CommonReturnType.create(null);
    }

    private UserModel convert2Model(UserForm userForm) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userForm == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userForm, userModel);
        userModel.setEncrptPassword(this.encodeByMD5(userForm.getEncrptPassword()));
        userModel.setRegisterMode("byTelPhone");

        return userModel;
    }

    public String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(MD5.digest(str.getBytes("utf-8")));

        return new String(encode);
    }


//    public CommonReturnType login(@RequestParam("telphone")String telphone,@RequestParam("encrptPassword")String password) throws BusinessException {
//
//        if (StringUtils.isEmpty(telphone)||StringUtils.isEmpty(password)) {
//            throw new BusinessException(EMBusinessError.PARAM_ERROR, "用户名或密码不正确");
//        }
//
//    }

}
