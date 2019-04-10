package com.zhangwk.eurekaclient.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MqReceiver {
 /*   //不会自动创建队列
    @RabbitListener(queues = "myQueue1")
    public void receiver1 (String message){
        log.info("MqReceiver1: {}", message);
    }*/

    @RabbitListener(queuesToDeclare = @Queue("myQueue2"))
    public void receiver2 (String message){
        log.info("MqReceiver2: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue3"),
            exchange = @Exchange("myExchange")
    ))
    public void receiver3 (String message){
        log.info("MqReceiver3: {}", message);
    }

    /**
     * 数码供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerQueue")
    ))
    public void computerReceiver (String message){
        log.info("computerReceiver: {}", message);
    }


    /**
     * 水果供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitQueue")
    ))
    public void fruitReceiver (String message){
        log.info("fruitReceiver: {}", message);
    }
}
