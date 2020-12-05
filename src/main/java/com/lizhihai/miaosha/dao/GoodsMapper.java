package com.lizhihai.miaosha.dao;

import com.lizhihai.miaosha.vo.entity.Goods;
import com.lizhihai.miaosha.vo.entity.MiaoshaGoods;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.*;

@Mapper
public interface GoodsMapper {

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    public List<GoodsParam> listGoodsParam();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id where g.id=#{goodsId}")
    public GoodsParam getGoodsDetailByGoodsId(@Param("goodsId")Long goodsId);

    @Update("update  miaosha_goods set stock_count=stock_count-1 where goods_id=#{goodsId} and stock_count>0 ")
    public int reduceStock(MiaoshaGoods goods);
}