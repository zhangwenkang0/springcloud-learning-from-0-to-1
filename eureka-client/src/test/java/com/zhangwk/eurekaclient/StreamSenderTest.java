package com.zhangwk.eurekaclient;

import com.zhangwk.eurekaclient.message.StreamClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamSenderTest {
    @Autowired
    private StreamClient streamClient;

    @Test
    public void sendTest(){
        streamClient.outPut().send(MessageBuilder.withPayload("hello! I am Stream Message!").build());
    }
}
