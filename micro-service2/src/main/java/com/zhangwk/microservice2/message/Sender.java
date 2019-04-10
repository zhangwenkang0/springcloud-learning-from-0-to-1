package com.zhangwk.microservice2.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Sender {
    //消息通道名称
    String OUTPUT = "input";

    @Output(OUTPUT)
    MessageChannel output();
}
