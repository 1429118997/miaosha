package com.lizhihai.miaosha.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lizhihai.miaosha.dao.MiaoshaOrderMapper;
import com.lizhihai.miaosha.vo.entity.MiaoshaOrder;
@Service
public class MiaoshaOrderService{

    @Resource
    private MiaoshaOrderMapper miaoshaOrderMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return miaoshaOrderMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(MiaoshaOrder record) {
        return miaoshaOrderMapper.insert(record);
    }

    
    public int insertSelective(MiaoshaOrder record) {
        return miaoshaOrderMapper.insertSelective(record);
    }

    
    public MiaoshaOrder selectByPrimaryKey(Long id) {
        return miaoshaOrderMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(MiaoshaOrder record) {
        return miaoshaOrderMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(MiaoshaOrder record) {
        return miaoshaOrderMapper.updateByPrimaryKey(record);
    }

}
