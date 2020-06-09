package com.wushiyuan.basicmall.order.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 使用 JSON 序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制 RabbitTemplate
     * 1、服务收到消息就回调
     *      1、spring.rabbitmq.publisher-confirms=true
     *      2、设置确认回调 ConfirmCallback
     * 2、消息正确抵达队列进行回调
     *      1、  #开启发送端消息抵达队列的确认
     *          spring.rabbitmq.publisher-returns=true
     *          #只要抵达队列，以异步发送优先回调 return 确认
     *          spring.rabbitmq.template.mandatory=true
     *      2、设置确认回调 ReturnCallback
     *
     * 3、消费端确认（保证每个消息被正确消费，此时才可以 broker 删除这个消息）
     *      spring.rabbitmq.listener.simple.acknowledge-mode=manual
     *      1、默认是自动确认的，只要消息接收到，客户端会自动确认，服务端就会移除这个消息
     *          问题：接受消息处理的程序宕机，消息会丢失
     *
     *          需要手动确认
     *
     *          开启消费者手动确认模式。只要消费者没有明确通知 MQ 消息就一直是 unacked 状态。即使 Consumer 宕机，消息状态会变成 Ready，下一个 consumer 会消费消息
     *
     *          消费者如何通知 MQ 消费成功呢？
     *              channel.basicAck(deliveryTag, false); 签收
     *              channel.basicNack(deliveryTag, false, true);拒签
     *
     *
     */
    @PostConstruct  //MyRabbitConfig 对象创建完成以后，执行这个方法
    public void initTemplate() {
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 1、只要消息抵达 Broker 就 ack=true
             * @param correlationData   当前消息的唯一关联数据（这个是消息的唯一id）
             * @param ack   消息是否成功收到
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm...correlationData["+correlationData+"]====>ack["+ack+"]====>cause["+cause+"]");
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败的回调
             * @param message   投递失败的消息详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时这个消息发给哪个交换机
             * @param routingKey    当时这个消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Message["+message+"]====>replyCode["+replyCode+"]====>replyText["+replyText+"]====>exchange["+exchange+"]====>routingKey["+routingKey+"]");
            }
        });
    }
}
