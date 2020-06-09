package com.wushiyuan.basicmall.order;

import com.wushiyuan.basicmall.order.entity.OrderEntity;
import com.wushiyuan.basicmall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootTest
class BasicmallOrderApplicationTests {

    /**
     * 1、如何创建 Exchange、Queue、Binding
     *      1）、使用 AmqpAdmin 进行创建
     * 2、如何收发消息
     */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void sendMessageTest() {
        for (int i = 0;i < 10; i ++) {
            if (i%2 == 0) {
                OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
                reasonEntity.setId(1l);
                reasonEntity.setCreateTime(new Date());
                reasonEntity.setName("钱不够");
                //1、发送消息，如果发送的消息是个对象，使用序列化机制，将对象写出去。类必须实现  Serializable 接口
                //2、发送的对象可以是一个 json

                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", reasonEntity, new CorrelationData(UUID.randomUUID().toString()));
            } else {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderSn(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", orderEntity, new CorrelationData(UUID.randomUUID().toString()));
            }
        }

        log.info("消息发送完成{}");
    }

    @Test
    void createExchanged() {
        //amqpAdmin
        //	public DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
        amqpAdmin.declareExchange(new DirectExchange("hello-java-exchange", true, false));
        log.info("Exchange[{}]创建成功", "hello-java-exchange");
    }

    @Test
    void createQueue() {
        //	public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete,
        //			@Nullable Map<String, Object> arguments) {
        amqpAdmin.declareQueue(new Queue("hello-java-queue", true, false, false));
        log.info("queue[{}]创建成功", "hello-java-queue");
    }

    @Test
    public void createBinding() {
        //	public Binding(String destination, DestinationType destinationType, String exchange, String routingKey,
        //			@Nullable Map<String, Object> arguments) {
        amqpAdmin.declareBinding(new Binding("hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null));
        log.info("binding[{}]创建成功", "hello-java-binding");
    }

}
