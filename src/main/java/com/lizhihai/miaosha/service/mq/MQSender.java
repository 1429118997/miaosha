package com.lizhihai.miaosha.service.mq;

import com.lizhihai.miaosha.config.rabbitmq.RabbitmqConfig;
import com.lizhihai.miaosha.util.BeanToStringUtil;
import com.lizhihai.miaosha.vo.param.MiaoShaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    private static Logger logger= LoggerFactory.getLogger(MQSender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(MiaoShaMessage message){
        logger.info("send:"+message.toString());
        String json = BeanToStringUtil.beanToString(message);
        rabbitTemplate.convertAndSend(RabbitmqConfig.SIMPLE_QUEUE,json);
    }
}
