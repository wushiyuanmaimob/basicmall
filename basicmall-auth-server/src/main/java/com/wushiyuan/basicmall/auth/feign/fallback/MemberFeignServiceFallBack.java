package com.wushiyuan.basicmall.auth.feign.fallback;

import com.wushiyuan.basicmall.auth.feign.MemberFeignService;
import com.wushiyuan.basicmall.auth.vo.UserLoginVo;
import com.wushiyuan.basicmall.auth.vo.UserRegistVo;
import com.wushiyuan.common.exception.BizCodeEnum;
import com.wushiyuan.common.to.member.MemberInfoTo;
import com.wushiyuan.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName MemberFeignServiceFallBack
 * @Info 会员服务调用失败的返回接口
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/23 13:58
 * @Version
 **/

@Slf4j
@Component
public class MemberFeignServiceFallBack implements MemberFeignService {
    @Override
    public R regist(UserRegistVo userRegistVo) {
        return R.error(BizCodeEnum.TOO_MANY_REQUEST.getCode(), BizCodeEnum.TOO_MANY_REQUEST.getMsg());
    }

    @Override
    public R<MemberInfoTo> login(UserLoginVo vo) {
        log.info("熔断方法调用...login");
        return R.error(BizCodeEnum.TOO_MANY_REQUEST.getCode(), BizCodeEnum.TOO_MANY_REQUEST.getMsg());
    }
}
