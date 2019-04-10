package com.zhangwk.microservice1.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
//@EnableBinding注解可以接收一个或多个接口类作为对象
// 声明绑定的消息通到,实现与消息代理的连接
@EnableBinding(Receiver.class)
@Log4j2
public class StreamReceiver {

    @StreamListener(Receiver.INPUT)
    public void input(Message<String> message){
        log.info("StreamReceiver: {}", message.getPayload());
    }
}
