package com.lizhihai.miaosha.config.context;

import com.lizhihai.miaosha.vo.entity.MiaoshaUser;

public class UserContext {

    private static ThreadLocal<MiaoshaUser> userThreadLocal=new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user){
        userThreadLocal.set(user);
    }

    public static MiaoshaUser getUser(){
        return userThreadLocal.get();
    }
}
