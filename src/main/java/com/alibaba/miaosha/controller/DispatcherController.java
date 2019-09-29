package com.alibaba.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DispatcherController {


    @RequestMapping(value = {"/","/login"})
    public String  toLogin(){

        return "html/login";
    }


    @RequestMapping("/itemList")
    public String  toItemList(){
        return "html/itemList";
    }



    @RequestMapping("/addItem")
    public String  toaddItem(){
        return "html/addItem";
    }

    @RequestMapping("/register")
    public String  toRegister(){
        return "html/register";
    }

    @RequestMapping("/getItem")
    public String  toGetItem(){
        return "html/getItem";
    }


}
