package com.lizhihai.miaosha.vo.param;

import com.lizhihai.miaosha.vo.entity.Goods;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;

public class GoodsDetailParam {
    private GoodsParam goods;
    private Integer miaoshaStatus;
    private Integer remianSecond;
    private MiaoshaUser user;

    public GoodsParam getGoods() {
        return goods;
    }

    public void setGoods(GoodsParam goods) {
        this.goods = goods;
    }

    public Integer getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(Integer miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public Integer getRemianSecond() {
        return remianSecond;
    }

    public void setRemianSecond(Integer remianSecond) {
        this.remianSecond = remianSecond;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
