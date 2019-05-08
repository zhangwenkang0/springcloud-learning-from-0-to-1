
import com.zhangwk.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MqSenderTest{
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send1(){
        System.out.println(new Date() +"发送延迟消息!!!");
        amqpTemplate.convertAndSend("delay_buffer_queue","Hello!Delay Message!");
    }

    @Test
    public void send2(){
        System.out.println(new Date() +"发送延迟重试消息!!!");
        //直接发消息到实际消费队列
        amqpTemplate.convertAndSend("retry_service_queue","Hello!Retry Message!");
    }

}
