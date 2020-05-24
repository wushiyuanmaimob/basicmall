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
 * 3.JSR303 规定了数据校验相关标准
 *      1)、给 Bean 添加校验注解 （所有注解类在 javax.validation.constraints包中）可以定义自己的message提示
 *      2)、开启校验功能 @Valid
 *      3)、给校验的 bean 后紧跟 BindingResult，可以获取到校验的结果
 *      4)、分组校验
 *         1)、 @NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
 *         给校验注解标注什么情况需要标注
 *         2)、@Validated({AddGroup.class})
 *
 * 4.统一的异常处理
 * @ControllerAdvice
 * 1)、
 */
@MapperScan("com.wushiyuan.basicmall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class BasicmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicmallProductApplication.class, args);
    }

}
