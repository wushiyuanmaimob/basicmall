package com.wushiyuan.basicmall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @Info
 * 1、整合 Sentinel
 *      1）、导入依赖 spring-cloud-starter-alibaba-sentinel
 *      2）、下载 sentinel 的控制台
 *      3）、配置 sentinel 控制台地址信息
 *      4）、在控制台调整参数。【默认所有的流控设置保存在内存中，重启失效】
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/23 10:43
 *
 * 2、每一个微服务都导入 actuator；并配合 management.endpoints.web.exposure.include*
 * 3、自定义 sentinel 流控返回数据
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BasicmallSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicmallSeckillApplication.class, args);
    }

}
