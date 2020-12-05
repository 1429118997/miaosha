package com.lizhihai.miaosha.vo.param;

import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.entity.OrderInfo;

public class OrderDetailParam {
    public OrderInfo order;
    public GoodsParam goods;

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }

    public GoodsParam getGoods() {
        return goods;
    }

    public void setGoods(GoodsParam goods) {
        this.goods = goods;
    }
}
