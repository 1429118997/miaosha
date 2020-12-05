package com.lizhihai.miaosha.dao;

import com.lizhihai.miaosha.vo.entity.MiaoshaOrder;
import com.lizhihai.miaosha.vo.entity.OrderInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long usdrId,@Param("goodsId") long goodsId);
    @Insert(
    "INSERT INTO `miaosha`.`order_info` (`user_id`,`goods_id`,`delivery_addr_id`,`goods_name`,`goods_count`,`goods_price`,`order_channel`,`status`,`create_date`,`pay_date`)"+
    "VALUES(#{userId} ,#{goodsId} ,#{deliveryAddrId} ,#{goodsName} ,#{goodsCount} ,#{goodsPrice} ,#{orderChannel} ,#{status} ,#{createDate} ,#{payDate} )"
    )
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    Long createOrder(OrderInfo orderInfo);

    @Insert(
            "INSERT INTO `miaosha`.`miaosha_order` (`user_id`,`order_id`,`goods_id`)"+
            "VALUES (#{userId} ,#{orderId},#{goodsId} ) ;"
    )
    @SelectKey(keyProperty = "id",keyColumn = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    Long insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id=#{orderId}")
    OrderInfo getOrderByGoodsId(Long orderId);
}
