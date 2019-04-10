package com.zhangwk.eurekaclient.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    public void receiver(Object message){
        log.info("StreamReceiver: {}",message);
    }
}
