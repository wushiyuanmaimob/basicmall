package com.wushiyuan.basicmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wushiyuan.basicmall.member.exception.PhoneExistException;
import com.wushiyuan.basicmall.member.exception.UserNameExistException;
import com.wushiyuan.basicmall.member.feign.CouponFeignService;
import com.wushiyuan.basicmall.member.vo.UserLoginVo;
import com.wushiyuan.basicmall.member.vo.UserRegistVo;
import com.wushiyuan.common.exception.BizCodeEnum;
import com.wushiyuan.common.to.member.MemberInfoTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wushiyuan.basicmall.member.entity.MemberEntity;
import com.wushiyuan.basicmall.member.service.MemberService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.R;



/**
 * 会员
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 15:58:51
 */

@Slf4j
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @PostMapping("regist")
    public R regist(@RequestBody UserRegistVo userRegistVo) {
        try {
            memberService.regist(userRegistVo);
        } catch (UserNameExistException e) {
            return R.error(BizCodeEnum.MEM_USER_NAME_EXIST.getCode(), BizCodeEnum.MEM_USER_NAME_EXIST.getMsg());
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnum.MEM_USER_PHONE_EXIST.getCode(), BizCodeEnum.MEM_USER_PHONE_EXIST.getMsg());
        }

        return R.ok();
    }

    public R<MemberInfoTo> blockHandler(@RequestBody UserLoginVo vo, BlockException e) {
        log.error("login被限流了...");
        return R.error(007, "then fuck");
    }

    /**
     * @Info 会员登录
     * @Author wushiyuanwork@outlook.com
     * @param vo : 登录信息
     * @return com.wushiyuan.common.utils.R
     * @throws
     * @Date 2020/6/18 19:19
     * @Version
     */
    @PostMapping("login")
    @SentinelResource(value = "do-login2345", blockHandler = "blockHandler")
    public R<MemberInfoTo> login(@RequestBody UserLoginVo vo) {

        //使用 try catch 自定义保护资源
        try (Entry entry = SphU.entry("do-login")) {
            MemberEntity member = memberService.login(vo);
            if (member == null) {
                return R.error(BizCodeEnum.AUTH_LOGIN_EXCEPTION.getCode(), BizCodeEnum.AUTH_LOGIN_EXCEPTION.getMsg());
            }
            R<MemberInfoTo> r = R.ok();
            MemberInfoTo memberInfoTo = new MemberInfoTo();
            BeanUtils.copyProperties(member, memberInfoTo);
            r.setData(memberInfoTo);

            return r;
        } catch (BlockException ex) {
            log.error("资源被限流，{}", ex.getMessage());
            return R.error(BizCodeEnum.TOO_MANY_REQUEST.getCode(), BizCodeEnum.TOO_MANY_REQUEST.getMsg());
        }
    }

    @RequestMapping("/coupons")
    public R test()
    {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");

        R memberCoupons = couponFeignService.memberCoupons();

        return R.ok().put("member", memberEntity).put("coupons", memberCoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
