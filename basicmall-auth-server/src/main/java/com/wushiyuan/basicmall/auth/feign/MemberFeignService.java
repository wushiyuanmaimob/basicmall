package com.wushiyuan.basicmall.auth.feign;

import com.wushiyuan.basicmall.auth.to.MemberEntity;
import com.wushiyuan.basicmall.auth.vo.UserLoginVo;
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

    /**
     * @Info 远程会员服务登录方法
     * @Author wushiyuanwork@outlook.com
     * @param vo : 登录信息
     * @return com.wushiyuan.common.utils.R
     * @throws
     * @Date 2020/6/18 18:55
     * @Version
     */
    @PostMapping("/member/member/login")
    R<MemberEntity> login(@RequestBody UserLoginVo vo);
}
