package com.lizhihai.miaosha.vo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaOrder {
    private Long id;

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 订单ID
    */
    private Long orderId;

    /**
    * 商品ID
    */
    private Long goodsId;
}