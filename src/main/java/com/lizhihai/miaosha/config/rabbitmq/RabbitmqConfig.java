package com.lizhihai.miaosha.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqConfig {

    public final static String SIMPLE_QUEUE="simple_queue";

    @Bean
    public Queue getSimpleQueue(){
        return new Queue(SIMPLE_QUEUE,true);
    }

}
