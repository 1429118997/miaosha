package com.lizhihai.miaosha.service;

import com.lizhihai.miaosha.vo.entity.MiaoshaGoods;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.lizhihai.miaosha.vo.entity.Goods;
import com.lizhihai.miaosha.dao.GoodsMapper;

import java.util.*;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsParam> listGoodsParam() {
        return goodsMapper.listGoodsParam();
    }

    public GoodsParam getGoodsDetailByGoodsId(long goodsId) {
        return goodsMapper.getGoodsDetailByGoodsId(goodsId);
    }

    public int reduceStock(GoodsParam detail) {
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(detail.getId());
        return goodsMapper.reduceStock(goods);
    }
}
