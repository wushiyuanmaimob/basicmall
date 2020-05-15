package com.wushiyuan.basicmall.product;

import com.wushiyuan.basicmall.product.entity.BrandEntity;
import com.wushiyuan.basicmall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicmallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = brandService.getById(1l);
        brandEntity.setLogo("https://baidu.com/i.img");
        brandService.updateById(brandEntity);
        System.out.println(brandEntity);
    }

}
