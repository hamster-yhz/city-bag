package com.op.citybag.demos.rabbitmq;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@Slf4j
public class RabbitMQConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback{
    
    // 点赞消息队列配置
    public static final String LIKE_QUEUE = "like.queue";
    public static final String LIKE_EXCHANGE = "like.exchange";
    public static final String LIKE_ROUTING_KEY = "like.routingKey";

//    @Autowired
//    @Lazy
//    private RabbitTemplate rabbitTemplate;
//
//    @PostConstruct
//    public void initRabbitMQ() {
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnsCallback(this);
//    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build());
    }

    @Bean
    public Queue likeQueue() {
        return new Queue(LIKE_QUEUE, true);
    }

    @Bean
    public DirectExchange likeExchange() {
        return new DirectExchange(LIKE_EXCHANGE);
    }

    @Bean
    public Binding bindingLike() {
        return BindingBuilder.bind(likeQueue())
               .to(likeExchange()).with(LIKE_ROUTING_KEY);
    }

    // 确认消息是否成功送达交换机
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData != null) {
            String msgId = correlationData.getId();
            if (ack) {
//                log.info("消息成功送达交换机，消息ID：{}", msgId);
            } else {
                log.info("消息未能送达交换机，消息ID：{}，原因：{}", msgId, cause);
                // 添加重试逻辑
            }
        }

    }

    // 确认消息是否被正确路由到队列
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息被退回：\n交换机：{}\n路由键：{}\n回应码：{}\n回应信息：{}\n消息内容：{}",
                returnedMessage.getExchange(),
                returnedMessage.getRoutingKey(),
                returnedMessage.getReplyCode(),
                returnedMessage.getReplyText(),
                new String(returnedMessage.getMessage().getBody()));

        // 添加消息重发逻辑
    }
}
