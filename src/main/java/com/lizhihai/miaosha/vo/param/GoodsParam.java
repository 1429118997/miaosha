package com.lizhihai.miaosha.vo.param;

import com.lizhihai.miaosha.vo.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsParam extends Goods {
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
