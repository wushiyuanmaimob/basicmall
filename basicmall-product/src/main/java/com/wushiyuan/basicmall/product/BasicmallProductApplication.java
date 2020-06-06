package com.wushiyuan.basicmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
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
 *         3)、默认没有指定分组校验注解 @NotBlank ，在分组校验 @Validated({AddGroup.class} 情况下不生效，只会在 @Validated 生效；
 *
 *      5)、自定义校验
 *          1）、编写一个自定义的校验注解
 *          2）、编写一个自定义的校验器
 *          3）、关联自定义的校验器和自定义的校验注解
 *
 * 4.统一的异常处理
 * @ControllerAdvice
 * 1)、编写异常处理类，使用@ControllerAdvice
 * 2)、使用@ExceptionHandler标注方法可以处理的异常
 *
 * 5、模板引擎
 *  1）、thymeleaf-start ;关闭缓存
 *  2）、静态资源都放在 static 文件夹下就可以按照路径直接访问
 *  3）、页面放在 templates下，直接访问
 *      SprintBoot，访问项目的时候，默认会找 index
 *  4）、页面修改不重启服务器实时更新
 *      1）、引入 dev-tools
 *      2）、ctrl+shift +F9 重新编译当前页面
 *
 *  6、整合 redis
 *      1）、引入 data-redis-starter
 *      2）、简单配置 redis 的 hosts 等信息
 *      3）、使用 SpringBoot 自动配置好的 StringRedisTemplate 来操作 redis
 *
 *  7 、整合 redisson
 *      1）、引入依赖<!-- https://mvnrepository.com/artifact/org.redisson/redisson -->
 * <dependency>
 *     <groupId>org.redisson</groupId>
 *     <artifactId>redisson</artifactId>
 *     <version>3.13.0</version>
 * </dependency>
 *      2）、配置 redisson
 *          MyRedissonConfig 给容器中配置一个 RedissonClient实例即可
 *      3）、使用
 *          参照文档
 *
 *  8、整合SpringCache简化缓存开发
 *      1）、引入依赖
 *          spring-boot-starter-cache、spring-boot-start-data-redis
 *      2）、写配置
 *          1）、自动配置
 *          2）、配置使用redis作为缓存
 *      3）、测试使用缓存
 *          @Cacheable: Triggers cache population. 触发将数据保存到缓存的操作
 *
 *          @CacheEvict: Triggers cache eviction.  触发将数据从缓存中删除的操作
 *
 *          @CachePut: Updates the cache without interfering with the method execution.  不影响方法执行更新缓存
 *
 *          @Caching: Regroups multiple cache operations to be applied on a method.  组合以上多个操作
 *
 *          @CacheConfig: Shares some common cache-related settings at class-level.  在类级别共享缓存的相同配置
 *
 *              1）开启缓存功能 @EnableCaching
 *              2）只需要使用注解就能完成缓存操作
 *              3）默认行为
 *                  1）、如果缓存中有，方法不用调用
 *                  2）、key默认自动
 *                  3）、缓存的 value 的值，默认使用 jdk 序列化机制，将序列化后的数据存到 redis
 *                  4）、默认 ttl 时间 -1
 *
 *                 自定义：
 *                      1）、指定生成缓存使用的 key
 *                      2）、指定缓存的数据的存活时间
 *                      3）、将数据保存为 json 格式
 *                          自定义 RedisCacheConfiguration 即可
 *
 *       4）、Spring-Cache的不足
 *       读模式
 *
 */
@EnableCaching
@MapperScan("com.wushiyuan.basicmall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class BasicmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicmallProductApplication.class, args);
    }

}
