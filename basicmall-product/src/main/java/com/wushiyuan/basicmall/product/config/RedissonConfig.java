package com.wushiyuan.basicmall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {
    /**
     * 使用 RedissonClient 操作 redis
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.10:6379");

        //2、根据 config 创建出 redissonClient 实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
