package com.wushiyuan.basicmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1.整合 MyBatis-Plus
 *      1），导入依赖
 *      <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.2.0</version>
 *         </dependency>
 *      2).配置
 *              1.配置数据源
 *                  1).导入数据驱动
 *                  2).在 application.yml 配置数据源相关信息
 *              2.配置 MyBatis-Plus
 *                  1).使用 @MapperScan
 *                  2).告诉 Mybatis-Plus sql映射配置文件
 *
 * 2.逻辑删除
 */
@MapperScan("com.wushiyuan.basicmall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class BasicmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicmallProductApplication.class, args);
    }

}
