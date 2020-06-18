package com.wushiyuan.basicmall.auth.feign;

import com.wushiyuan.basicmall.auth.vo.UserRegistVo;
import com.wushiyuan.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName MemberFeignService
 * @Info 会员服务远程调用接口
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/18 16:05
 * @Version
 **/
@FeignClient("basicmall-member")
public interface MemberFeignService {
    /**
     * @Info 远程会员服务注册方法
     * @Author wushiyuanwork@outlook.com
     * @param userRegistVo : 注册信息
     * @return com.wushiyuan.common.utils.R
     * @throws
     * @Date 2020/6/18 16:10
     * @Version
     */
    @PostMapping("/member/member/regist")
    R regist(@RequestBody UserRegistVo userRegistVo);
}
