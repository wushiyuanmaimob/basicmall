package com.wushiyuan.basicmall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1.如何使用 Nacos 作为配置中心同意管理配置
 * 1).引入依赖
 * <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *         </dependency>
 * 2).创建一个 bootstrap.properties.
 *      spring.application.name=basicmall-coupon
 *      spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 * 3).需要给配置中心默认添加一个叫 数据集 （Data Id）basicmall-coupon.properties.默认规则，应用名.properties
 * 4).给应用名.properties添加任何配置
 * 5).动态获取配置 @RefreshScope 动态获取并刷新配置
 * @Value(${配置项的名})，获取配置
 * 如果配置中心和当前应用的配置文件都配置了相同的项，优先使用配置中心的配置
 *
 * 2.细节
 * 1)、命名空间；配置隔离；
 *      默认：public（保留空间）默认新增的所有配置都在 public 空间。
 *      1、开发，测试，生产 利用命名空间来做环境隔离
 *      使用 spring.cloud.nacos.config.namespace=242295d1-041a-488b-9f44-0f5eb574c054 配置
 *      2、每一个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己命名空间下的所有配置 使用配置分组区分环境 dev，test，prod
 *
 *      3、同时加载多个配置集
 *          1)、
 *
 * 2)、配置集
 * 3)、配置集ID
 * 4)、配置分组
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BasicmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicmallCouponApplication.class, args);
    }

}
