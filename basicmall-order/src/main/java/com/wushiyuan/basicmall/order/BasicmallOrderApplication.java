package com.wushiyuan.basicmall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * rabbitmq 使用
 * 1、引入 amqp 场景 RabbitAutoConfiguration 会自动生效
 * 2、给 容器中自动配置了
 *      rabbitTemplate AmqpAdmin CachingConnectionFactory RabbitMessagingTemplate
 *
 * 3、给配置文件中配置 spring.rabbitmq 信息
 * 4、@EnableRabbit 注解 开启功能
 * 5、监听消息：使用 @RabbitListener；必须有@EnableRabbit
 * @RabbitListener：类 + 方法 （注解作用的范围）
 * @RabbitHander：方法 上（注解的作用范围）
 */
@EnableRabbit
@SpringBootApplication
public class BasicmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicmallOrderApplication.class, args);
    }

}
