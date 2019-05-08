package com.zhangwk.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class MqConfig implements RabbitListenerConfigurer {

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    /**
     * 设置消费者Json消息转换器
     * @return
     */
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    /**
     * BasicProperties和Message Properties对象之间互相转换
     * @return
     */
    @Bean
    public MessagePropertiesConverter messagePropertiesConverter(){
        return new DefaultMessagePropertiesConverter();
    }
    /**
     * 消息过期时间 3秒
     */
    private Integer QUEUE_EXPIRATION = 3 * 1000;

    //---------------------------------------------延迟消费实例----------------------------------------------------------------
    /**
     * 缓冲队列
     */
    private String DELAY_BUFFER_QUEUE = "delay_buffer_queue";

    /**
     * 实际消费交换机(DLX)
     */
    private String DELAY_SERVICE_EXCHANGE = "delay_service_exchange";

    /**
     * 实际消费队列
     */
    private String DELAY_SERVICE_QUEUE = "delay_service_queue";


    /**
     * 实际消费队列
     * @return
     */
    @Bean
    Queue delayServiceQueue(){
        return QueueBuilder.durable(DELAY_SERVICE_QUEUE).build();
    }

    /**
     * 实际消费交换机
     * @return
     */
    @Bean
    DirectExchange delayServiceExchange() {
        return new DirectExchange(DELAY_SERVICE_EXCHANGE);
    }

    /**
     * 实际消费队列绑定实际消费交换机(DLX)
     * @param delayServiceQueue
     * @param delayServiceExchange
     * @return
     */
    @Bean
    Binding delayBinding(Queue delayServiceQueue, DirectExchange delayServiceExchange) {
        return BindingBuilder.bind(delayServiceQueue)
                .to(delayServiceExchange)
                .with(DELAY_SERVICE_QUEUE);
    }

    /**
     * 缓冲队列配置
     * @return
     */
    @Bean
    Queue delayBufferQueue(){
        return QueueBuilder.durable(DELAY_BUFFER_QUEUE)
                // 死信交换机 DLX
                .withArgument("x-dead-letter-exchange", DELAY_SERVICE_EXCHANGE)
                // 目标routing key
                .withArgument("x-dead-letter-routing-key", DELAY_SERVICE_QUEUE)
                // 设置队列的过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }


    //---------------------------------------------延迟重试实例----------------------------------------------------------------
    /**
     * 缓冲队列
     */
    private String RETRY_BUFFER_QUEUE = "retry_buffer_queue";
    /**
     * 缓冲交换机
     */
    private String RETRY_BUFFER_EXCHANGE = "retry_buffer_exchange";

    /**
     * 实际消费交换机(DLX)
     */
    private String RETRY_SERVICE_EXCHANGE = "retry_service_exchange";

    /**
     * 实际消费队列
     */
    private String RETRY_SERVICE_QUEUE = "retry_service_queue";


    /**
     * 实际消费队列
     * @return
     */
    @Bean
    Queue retryServiceQueue(){
        return QueueBuilder.durable(RETRY_SERVICE_QUEUE).build();
    }

    /**
     * 实际消费交换机
     * @return
     */
    @Bean
    DirectExchange retryServiceExchange() {
        return new DirectExchange(RETRY_SERVICE_EXCHANGE);
    }

    /**
     * 实际消费队列绑定实际消费交换机(DLX)
     * @param retryServiceQueue
     * @param retryServiceExchange
     * @return
     */
    @Bean
    Binding retryBinding(Queue retryServiceQueue, DirectExchange retryServiceExchange) {
        return BindingBuilder.bind(retryServiceQueue)
                .to(retryServiceExchange)
                .with(RETRY_SERVICE_QUEUE);
    }

    /**
     * 缓冲队列配置
     * @return
     */
    @Bean
    Queue retryBufferQueue(){
        return QueueBuilder.durable(RETRY_BUFFER_QUEUE)
                // 死信交换机 DLX
                .withArgument("x-dead-letter-exchange", RETRY_SERVICE_EXCHANGE)
                // 目标routing key
                .withArgument("x-dead-letter-routing-key", RETRY_SERVICE_QUEUE)
                // 设置队列的过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 缓冲交换机
     * @return
     */
    @Bean
    DirectExchange retryBufferExchange() {
        return new DirectExchange(RETRY_BUFFER_EXCHANGE);
    }

    /**
     * 缓冲队列绑定缓冲交换机
     * @param retryBufferQueue
     * @param retryBufferQueue
     * @return
     */
    @Bean
    Binding bufferBinding(Queue retryBufferQueue, DirectExchange retryBufferExchange) {
        return BindingBuilder.bind(retryBufferQueue)
                .to(retryBufferExchange)
                .with(RETRY_BUFFER_QUEUE);
    }
}
