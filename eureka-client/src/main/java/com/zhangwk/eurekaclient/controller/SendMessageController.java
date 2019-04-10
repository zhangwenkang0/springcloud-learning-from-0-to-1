package com.zhangwk.eurekaclient.controller;

import com.zhangwk.eurekaclient.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {
    @Autowired
    MessageChannel output;


    @GetMapping("send")
    public void sendStreamMessage(){
        output.send(MessageBuilder.withPayload("hello! I am Stream Message!").build());
    }
}
