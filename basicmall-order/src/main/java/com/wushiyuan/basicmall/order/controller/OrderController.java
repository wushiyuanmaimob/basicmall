package com.wushiyuan.basicmall.order.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.wushiyuan.basicmall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wushiyuan.basicmall.order.entity.OrderEntity;
import com.wushiyuan.basicmall.order.service.OrderService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.R;



/**
 * 订单
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 16:34:38
 */
@RestController
@RequestMapping("order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{num}")
    public R sendMessage(@PathVariable("num") Long num) {
        for (int i = 0;i < num; i ++) {
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

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
