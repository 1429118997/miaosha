package com.lizhihai.miaosha.config.redis.key;

import com.lizhihai.miaosha.vo.entity.MiaoshaUser;

public class MiaoshaUserKey extends BasePrefix {

    private final static int expireSeconds = 3600 * 24 * 2;

    public static MiaoshaUserKey token = new MiaoshaUserKey(expireSeconds, "tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey getKey(int expireSeconds, String prefix){
        return new  MiaoshaUserKey(expireSeconds,prefix);
    }
}
