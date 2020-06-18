package com.wushiyuan.basicmall.auth.controller;

import com.wushiyuan.basicmall.auth.feign.MemberFeignService;
import com.wushiyuan.basicmall.auth.feign.ThirdPartyFeignService;
import com.wushiyuan.basicmall.auth.vo.UserRegistVo;
import com.wushiyuan.common.constant.AuthServerConstant;
import com.wushiyuan.common.exception.BizCodeEnum;
import com.wushiyuan.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberFeignService memberFeignService;

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
            if (System.currentTimeMillis() - l < 60 * 1000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            } else {
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

    /**
     * @Info 用户注册
     * @Author wushiyuanwork@outlook.com
     * @param userRegistVo : 注册信息
     * @return com.wushiyuan.common.utils.R
     * @Date 2020/6/18 16:11
     * @Version
     */
    @PostMapping("regist")
    public R regist(@Valid @RequestBody UserRegistVo userRegistVo) {
        //验证两次密码是否一致
        if (!userRegistVo.getPassword().equals(userRegistVo.getConfirmPassword())) {
            Map<String, String> msg = new HashMap<>();
            msg.put("confirmPassword", "确认密码错误");
            return R.error(BizCodeEnum.AUTH_USER_EXCEPTION.getCode(), BizCodeEnum.AUTH_USER_EXCEPTION.getMsg()).put("data", msg);
        }

        //验证验证码
        String codeRedisKey = AuthServerConstant.SMS_CODE_CACHE_PREFIX + userRegistVo.getPhone();
        String codeWithTime = redisTemplate.opsForValue().get(codeRedisKey);
        if (codeWithTime == null) {
            Map<String, String> msg = new HashMap<>();
            msg.put("code", "验证码错误");
            return R.error(BizCodeEnum.AUTH_USER_EXCEPTION.getCode(), BizCodeEnum.AUTH_USER_EXCEPTION.getMsg()).put("data", msg);
        }
        String[] split = codeWithTime.split("_");
        if (!userRegistVo.getCode().equals(split[0])) {
            Map<String, String> msg = new HashMap<>();
            msg.put("code", "验证码错误");
            return R.error(BizCodeEnum.AUTH_USER_EXCEPTION.getCode(), BizCodeEnum.AUTH_USER_EXCEPTION.getMsg()).put("data", msg);
        }

        //删除 redis 中的验证码
        redisTemplate.delete(codeRedisKey);

        //调用远程服务进行注册
        R r = memberFeignService.regist(userRegistVo);

        return r;
    }
}
