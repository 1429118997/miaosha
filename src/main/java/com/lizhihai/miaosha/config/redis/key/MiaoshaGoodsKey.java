package com.lizhihai.miaosha.config.redis.key;

public class MiaoshaGoodsKey extends BasePrefix {

    private final static int expireSeconds = 60;

    public static MiaoshaGoodsKey isGoodsOver = new MiaoshaGoodsKey(0, "isGoodsOver");
    public static MiaoshaGoodsKey miaoshaGoodsStock = new MiaoshaGoodsKey(0, "miaoshaGoodsStock");
    private MiaoshaGoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


}
