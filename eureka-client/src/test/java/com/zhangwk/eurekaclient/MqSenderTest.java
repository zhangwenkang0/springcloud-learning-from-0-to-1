package com.zhangwk.eurekaclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqSenderTest{
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send1(){
        amqpTemplate.convertAndSend("myQueue1","Hello!Mq Message!");
    }

    @Test
    public void send2(){
        amqpTemplate.convertAndSend("myQueue2","Hello!Mq Message!");
    }

    @Test
    public void sen3(){
        amqpTemplate.convertAndSend("myQueue3","Hello!Mq Message!");
    }


}
