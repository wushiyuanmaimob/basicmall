package com.wushiyuan.basicmall.thirdparty.contoller;

import com.wushiyuan.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsSendController {
    @GetMapping("sendCode")
    public R sendCode(@RequestParam("phone") String phone,@RequestParam("code") String code) {

        log.info("验证码已发送 {}", code);
        return R.ok();
    }
}
