package com.wushiyuan.basicmall.auth.controller;

import com.wushiyuan.basicmall.auth.feign.ThirdPartyFeignService;
import com.wushiyuan.common.constant.AuthServerConstant;
import com.wushiyuan.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {
        //1、接口放刷 redis 记录上次发送的时间，发送之前做判断

        //2、验证码再次校验 : 验证码保存至 redis 中

        String random = UUID.randomUUID().toString().replace("-", "");
        String code = random.substring(0, 6);
        String phoneRedisKey = AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone;
        String codeWithTime = redisTemplate.opsForValue().get(phoneRedisKey);
        if (null != codeWithTime) {
            long l = Long.parseLong(codeWithTime.split("_")[1]);
            if (System.currentTimeMillis() - l > 60 * 1000) {
                //60 秒以后再次请求才给发送
                thirdPartyFeignService.sendCode(phone, code);
            }
        } else {
            thirdPartyFeignService.sendCode(phone, code);
        }

        //验证码发送成功则将其拼接系统当前时间保存到 redis 中
        redisTemplate.opsForValue().set(phoneRedisKey, code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);

        return R.ok();
    }
}
