package com.wushiyuan.basicmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.wushiyuan.basicmall.member.exception.PhoneExistException;
import com.wushiyuan.basicmall.member.exception.UserNameExistException;
import com.wushiyuan.basicmall.member.feign.CouponFeignService;
import com.wushiyuan.basicmall.member.vo.UserRegistVo;
import com.wushiyuan.common.exception.BizCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
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
