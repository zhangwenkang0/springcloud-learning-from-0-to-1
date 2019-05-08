package com.zhangwk.retry;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class RetryMsgListener {
    /**
     * 缓冲队列
     */
    private String RETRY_BUFFER_QUEUE = "retry_buffer_queue";
    /**
     * 缓冲交换机
     */
    private String RETRY_BUFFER_EXCHANGE = "retry_buffer_exchange";

    @Autowired
    private MessagePropertiesConverter messagePropertiesConverter;

    @RabbitListener(queues="retry_service_queue")
    public void listenServiceMsg(@Payload Message message, Channel channel){
        try {
            System.out.println(new Date() + "收到消息:" + new String(message.getBody()));
            //TODO 业务逻辑
            //突然出现异常
            throw  new RuntimeException("特殊异常");
        }catch (Exception e){
            Map<String,Object> headers = message.getMessageProperties().getHeaders();
            try{
                Long retryCount = getRetryCount(headers);
                //重试3次
                if(retryCount < 3){
                    retryCount += 1;
                    System.out.println("消费异常,准备重试,第"+retryCount+"次");

                    //转换为RabbitMQ 的Message Properties对象
                    AMQP.BasicProperties rabbitMQProperties =
                            messagePropertiesConverter.fromMessageProperties( message.getMessageProperties(), "UTF-8");
                    //设置headers
                    rabbitMQProperties.builder().headers(headers);
                    //程序异常重试
                    //这里必须把rabbitMQProperties也传进来,否则死信队列无法识别是否是同一条信息,导致重试次数无法递增
                    channel.basicPublish(RETRY_BUFFER_EXCHANGE,RETRY_BUFFER_QUEUE,rabbitMQProperties, message.getBody());
                }else {
                    //TODO 重试失败,需要人工处理 (发送到失败队列或发邮件/信息)
                    System.out.println("已重试3次,需人工处理!");
                }
            }catch (IOException ioe){
                System.out.println("消息重试失败!");
                ioe.printStackTrace();
            }
        }
    }

    /**
     * 获取重试次数
     * 如果这条消息是死信,header中会有一个x-death的记录相关信息
     * 其中包含死亡次数
     * @param headers
     * @return
     */
    private long getRetryCount(Map<String, Object> headers) {
        long retryCount = 0;
        if(null != headers) {
            if(headers.containsKey("x-death")) {
                List<Map<String, Object>> deathList = (List<Map<String, Object>>) headers.get("x-death");
                if(!deathList.isEmpty()) {
                    Map<String, Object> deathEntry = deathList.get(0);
                    retryCount = (Long)deathEntry.get("count");
                }
            }
        }
        return retryCount;
    }
}


