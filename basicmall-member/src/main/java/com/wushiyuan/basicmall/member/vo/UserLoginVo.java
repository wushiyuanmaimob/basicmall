package com.wushiyuan.basicmall.member.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName UserLoginVo
 * @Info 用户登录vo
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/18 18:00
 * @Version
 **/
@Data
public class UserLoginVo {
    /**
     * @Info 可以是 用户名、邮箱，手机号
     * @Author wushiyuanwork@outlook.com
     * @Date 2020/6/18 18:01
     * @Version
     */
    @NotEmpty(message = "用户名/邮箱/手机号不能为空")
    private String userId;
    @NotEmpty
    private String password;
}
