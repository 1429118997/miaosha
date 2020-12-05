package com.lizhihai.miaosha.dao;

import com.lizhihai.miaosha.vo.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaUserMapper {

    @Select("select * from miaosha_user where id=#{id} ;")
    public MiaoshaUser getById(Long id);

    @Update("update miaosha_user set password=#{password} where id=#{id}")
    public long update(MiaoshaUser toBeUpdate);
}