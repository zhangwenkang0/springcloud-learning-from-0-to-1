package com.zhangwk.delay;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DelayMsgListener {
    @RabbitListener(queues="delay_service_queue")
    public void listenServiceMsg(Message message){
        System.out.println(new Date()+ "收到延迟消息啦:"+new String(message.getBody()));
    }
}
