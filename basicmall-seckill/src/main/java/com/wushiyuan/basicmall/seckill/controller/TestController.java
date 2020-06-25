package com.wushiyuan.basicmall.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Info
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/23 9:17
 * @Version
 **/

@Slf4j
@RestController
public class TestController {

    @GetMapping("hello")
    public String test() {
        log.info("test正在执行...");
        return "hello";
    }
}
