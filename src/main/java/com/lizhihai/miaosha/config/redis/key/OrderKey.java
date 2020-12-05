package com.lizhihai.miaosha.config.redis.key;

public class OrderKey extends BasePrefix {

    private final static int expireSeconds = 60;

    public static OrderKey orderDetail= new OrderKey(expireSeconds, "orderDetail");
    public static OrderKey miaoshaOrder= new OrderKey(0, "miaoshaOrder");
    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


}
