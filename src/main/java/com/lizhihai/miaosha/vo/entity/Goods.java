package com.lizhihai.miaosha.vo.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    /**
    * 商品ID
    */
    private Long id;

    /**
    * 商品名称
    */
    private String goodsName;

    /**
    * 商品标题
    */
    private String goodsTitle;

    /**
    * 商品的图片
    */
    private String goodsImg;

    /**
    * 商品的详情介绍
    */
    private String goodsDetail;

    /**
    * 商品单价
    */
    private BigDecimal goodsPrice;

    /**
    * 商品库存，-1表示没有限制
    */
    private Integer goodsStock;
}