package com.lizhihai.miaosha.dao;

import com.lizhihai.miaosha.vo.entity.MiaoshaOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MiaoshaOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaOrder record);

    int insertSelective(MiaoshaOrder record);

    MiaoshaOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaOrder record);

    int updateByPrimaryKey(MiaoshaOrder record);
}