package com.wushiyuan.basicmall.seckill.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wushiyuan.common.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName SeckillControllerAdvice
 * @Info
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/23 11:50
 * @Version
 **/

@RestControllerAdvice(basePackages = "com.wushiyuan.basicmall.seckill.controller")
public class SeckillControllerAdvice {

    @ExceptionHandler(value = BlockException.class)
    public R handBlockException() {
        return R.error(1, "fuck");
    }
}
