package com.wushiyuan.basicmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.basicmall.member.vo.UserLoginVo;
import com.wushiyuan.basicmall.member.vo.UserRegistVo;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 15:58:51
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(UserRegistVo userRegistVo);

    MemberEntity login(UserLoginVo vo);
}

