package com.lizhihai.miaosha.config.redis.key;

public class MiaoshaKey extends BasePrefix {

    private final static int expireSeconds = 3600 * 24 * 2;

    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");

    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(60, "vc");

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
