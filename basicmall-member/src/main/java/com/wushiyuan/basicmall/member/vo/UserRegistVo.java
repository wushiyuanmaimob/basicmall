package com.wushiyuan.basicmall.member.vo;

import lombok.Data;

@Data
public class UserRegistVo {
    private String name;

    private String password;

    private String confirmPassword;

    private String phone;

}
