package com.lizhihai.miaosha.controller;

import com.lizhihai.miaosha.service.mq.MQSender;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    MQSender mqSend;

    @RequestMapping("/user/token")
    public Result login(MiaoshaUser user, Model model) {
       return Result.success(null);
    }

//    @RequestMapping("/test")
//    public void login(){
//        mqSend.simpleSend("hello world");
//    }
}
