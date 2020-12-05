package com.lizhihai.miaosha.dao;

import com.lizhihai.miaosha.vo.entity.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaGoods record);

    int insertSelective(MiaoshaGoods record);

    MiaoshaGoods selectByPrimaryKey(Long id);
    @Update("update miaosha_goods mg set mg.start_date=#{startDate} where mg.id=#{id}")
    int updateByPrimaryKeySelective(MiaoshaGoods record);

    int updateByPrimaryKey(MiaoshaGoods record);
}