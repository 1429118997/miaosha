package com.lizhihai.miaosha.config.redis.key;

public interface KeyPrefix {

    public int expireSeconds();
    public String getPrefix();
}
