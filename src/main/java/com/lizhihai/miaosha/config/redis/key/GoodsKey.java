package com.lizhihai.miaosha.config.redis.key;

public class GoodsKey extends BasePrefix {

    private final static int expireSeconds = 60;

    public static GoodsKey goodsListKey = new GoodsKey(expireSeconds, "goodsListKey");
    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


}
