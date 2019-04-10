package com.zhangwk.microservice1.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Receiver {
    //消息通道名称
    String INPUT = "input";

    @Input(INPUT)
    SubscribableChannel input();
}
