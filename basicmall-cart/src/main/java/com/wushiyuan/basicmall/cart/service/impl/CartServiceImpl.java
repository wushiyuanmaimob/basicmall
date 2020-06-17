package com.wushiyuan.basicmall.cart.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CartServiceImpl {

    @Resource
    StringRedisTemplate redisTemplate;

}
