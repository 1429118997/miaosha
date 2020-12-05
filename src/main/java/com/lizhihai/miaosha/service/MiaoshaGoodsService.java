package com.lizhihai.miaosha.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lizhihai.miaosha.vo.entity.MiaoshaGoods;
import com.lizhihai.miaosha.dao.MiaoshaGoodsMapper;
@Service
public class MiaoshaGoodsService{

    @Resource
    private MiaoshaGoodsMapper miaoshaGoodsMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return miaoshaGoodsMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(MiaoshaGoods record) {
        return miaoshaGoodsMapper.insert(record);
    }

    
    public int insertSelective(MiaoshaGoods record) {
        return miaoshaGoodsMapper.insertSelective(record);
    }

    
    public MiaoshaGoods selectByPrimaryKey(Long id) {
        return miaoshaGoodsMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(MiaoshaGoods record) {
        return miaoshaGoodsMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(MiaoshaGoods record) {
        return miaoshaGoodsMapper.updateByPrimaryKey(record);
    }

}
