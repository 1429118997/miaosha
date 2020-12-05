package com.lizhihai.miaosha.service.mq;

import com.lizhihai.miaosha.config.rabbitmq.RabbitmqConfig;
import com.lizhihai.miaosha.service.GoodsService;
import com.lizhihai.miaosha.service.MiaoshaService;
import com.lizhihai.miaosha.service.OrderService;
import com.lizhihai.miaosha.util.BeanToStringUtil;
import com.lizhihai.miaosha.vo.entity.MiaoshaOrder;
import com.lizhihai.miaosha.vo.entity.OrderInfo;
import com.lizhihai.miaosha.vo.param.GoodsParam;
import com.lizhihai.miaosha.vo.param.MiaoShaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    private static Logger logger= LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @RabbitListener(queues = RabbitmqConfig.SIMPLE_QUEUE)
    public void recevice(String messgae){
        logger.info("receive: "+messgae);
        MiaoShaMessage bean = BeanToStringUtil.stringToBean(messgae, MiaoShaMessage.class);

        GoodsParam detail = goodsService.getGoodsDetailByGoodsId(bean.getGoodsId());

        if (detail.getStockCount()<=0){
            return;
        }

        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(bean.getUser().getId(), detail.getId());
        if (order!=null){
            return;
        }

        OrderInfo miaosha = miaoshaService.miaosha(bean.getUser(), detail);
    }

}
