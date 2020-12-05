package com.lizhihai.miaosha.vo.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaGoods {
    /**
    * 秒杀的商品表
    */
    private Long id;

    /**
    * 商品Id
    */
    private Long goodsId;

    /**
    * 秒杀价
    */
    private BigDecimal miaoshaPrice;

    /**
    * 库存数量
    */
    private Integer stockCount;

    /**
    * 秒杀开始时间
    */
    private Date startDate;

    /**
    * 秒杀结束时间
    */
    private Date endDate;
}