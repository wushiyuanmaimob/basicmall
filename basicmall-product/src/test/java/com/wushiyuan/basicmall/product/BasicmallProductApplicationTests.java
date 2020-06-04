package com.wushiyuan.basicmall.product;

import com.wushiyuan.basicmall.product.entity.BrandEntity;
import com.wushiyuan.basicmall.product.service.BrandService;
import com.wushiyuan.basicmall.product.service.impl.CategoryServiceImpl;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
class BasicmallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = brandService.getById(1l);
        brandEntity.setLogo("https://baidu.com/i.img");
        brandService.updateById(brandEntity);
        System.out.println(brandEntity);
    }

    @Test
    void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("hello", "world" + UUID.randomUUID().toString());
        String hello = ops.get("hello");
        System.out.println("之前保存的数据是：" + hello);
    }

    @Test
    void testFindCategoryPaths() {
        List<Long> categoryPath = categoryService.findCategoryPath(225L);
    }

    @Autowired
    RedissonClient redissonClient;

    @Test
    void testRedissonClient() {
        System.out.println(redissonClient);
    }

}
