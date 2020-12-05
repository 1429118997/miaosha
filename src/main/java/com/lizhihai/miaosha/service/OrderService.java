package com.lizhihai.miaosha.service;


import com.lizhihai.miaosha.config.redis.RedisService;
import com.lizhihai.miaosha.config.redis.key.OrderKey;
import com.lizhihai.miaosha.dao.OrderMapper;
import com.lizhihai.miaosha.vo.entity.MiaoshaOrder;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.entity.OrderInfo;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long usdrId, long goodsId) {
//        return orderMapper.getMiaoshaOrderByUserIdGoodsId(usdrId,goodsId);
       return redisService.get(OrderKey.miaoshaOrder, usdrId+"_"+goodsId,MiaoshaOrder.class);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsParam detail) {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(detail.getId());
        orderInfo.setGoodsName(detail.getGoodsName());
        orderInfo.setGoodsPrice(detail.getMiaoshaPrice());
        orderInfo.setOrderChannel((byte) 1);
        orderInfo.setStatus((byte) 0);
        orderInfo.setUserId(user.getId());
        orderMapper.createOrder(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(detail.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);
        redisService.set(OrderKey.miaoshaOrder,user.getId()+"_"+detail.getId(),miaoshaOrder);
        return orderInfo;
    }

    public OrderInfo getOrderByGoodsId(Long orderId) {
        return orderMapper.getOrderByGoodsId(orderId);
    }
}
