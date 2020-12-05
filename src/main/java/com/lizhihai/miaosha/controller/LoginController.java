package com.lizhihai.miaosha.controller;

import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.service.MiaoshaUserService;
import com.lizhihai.miaosha.util.ValidatorUtil;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.entity.Result;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import com.lizhihai.miaosha.vo.param.LoginParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    final static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @RequestMapping
    public String login(MiaoshaUser user) {
        if (user==null){
            return "/login";
        }else{
            return "forward:goods/to_list";
        }
    }

    @ResponseBody
    @RequestMapping("/do_login")
    public Result toLogin(HttpServletResponse response, @Valid LoginParam loginParam) {
        logger.info(loginParam.toString());
        //参数校验
        MiaoshaUser user = miaoshaUserService.login(response, loginParam);
        if (user==null) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        //生存cookie
        return Result.success(true);
    }

    @ResponseBody
    @RequestMapping("/create_token")
    public String createToken(HttpServletResponse response, @Valid LoginParam loginParam){
        logger.info(loginParam.toString());
        String token=miaoshaUserService.createToken(response,loginParam);
        return token;
    }
}
