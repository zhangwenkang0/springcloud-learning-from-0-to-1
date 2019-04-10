package com.zhangwk.microservice2.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Sender.class)
@Log4j2
public class SendController {
    @Autowired
    @Qualifier(Sender.OUTPUT)
    MessageChannel output;

    @GetMapping("send")
    public void send(){
        String message = "Hello! I am Stream Message!";
        log.info("发送Stream消息: {}",message);
        output.send(MessageBuilder.withPayload(message).build());
    }
}
