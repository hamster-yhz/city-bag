package com.op.citybag.demos.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    // 点赞消息队列配置
    public static final String LIKE_QUEUE = "like.queue";
    public static final String LIKE_EXCHANGE = "like.exchange";
    public static final String LIKE_ROUTING_KEY = "like.routingKey";
    
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
}
