package com.wushiyuan.basicmall.order.service.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.wushiyuan.basicmall.order.entity.OrderEntity;
import com.wushiyuan.basicmall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.order.dao.OrderItemDao;
import com.wushiyuan.basicmall.order.entity.OrderItemEntity;
import com.wushiyuan.basicmall.order.service.OrderItemService;


@RabbitListener(queues = "hello-java-queue")
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 注解的 queues 值 声明需要监听的所有队列
     *
     * @param message 原声消息详细信息。头+体
     * @param content 类型为发送消息的类型
     * @param channel 当前传输的数据通道
     */
    @RabbitHandler
    public void receiveMessage(Message message,
                               OrderReturnReasonEntity content,
                               Channel channel) {
        byte[] body = message.getBody();

        MessageProperties messageProperties = message.getMessageProperties();

        System.out.println("接收到消息..." + message + "====>内容：" + content);
    }

    @RabbitHandler
    public void receiveMessage2(Message message,
                               OrderEntity content,
                               Channel channel) {
        System.out.println("接收到消息" + content);
        byte[] body = message.getBody();

        MessageProperties messageProperties = message.getMessageProperties();

        System.out.println("接收到消息..." + message + "====>内容：" + content);

        //channel 内按顺序自增
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //消费者对消息执行是否接受的操作，非批量模式
        try {
            if (deliveryTag % 2 == 0) {
                //签收
                channel.basicAck(deliveryTag, false);
            } else {
                //拒收
                channel.basicNack(deliveryTag, false, true);
            }
        } catch (Exception e) {

        }
    }
}