package com.lizhihai.miaosha.controller;

import com.lizhihai.miaosha.service.GoodsService;
import com.lizhihai.miaosha.service.OrderService;
import com.lizhihai.miaosha.vo.entity.MiaoshaOrder;
import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import com.lizhihai.miaosha.vo.entity.OrderInfo;
import com.lizhihai.miaosha.vo.entity.Result;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import com.lizhihai.miaosha.vo.param.OrderDetailParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;

    @ResponseBody
    @RequestMapping("/detail")
    public Result getDetail(Long orderId, MiaoshaUser user){
        OrderInfo info=orderService.getOrderByGoodsId(orderId);
        if (info==null){
            return Result.error(CodeMsg.ORDERINFO_NOT_EXIST);
        }
        GoodsParam goods = goodsService.getGoodsDetailByGoodsId(info.getGoodsId());
        OrderDetailParam orderDetailParam = new OrderDetailParam();
        orderDetailParam.setOrder(info);
        orderDetailParam.setGoods(goods);
        return Result.success(orderDetailParam);
    }
}
