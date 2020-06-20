package com.wushiyuan.common.exception;

import lombok.Getter;

public enum  BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    SMS_CODE_EXCEPTION(10002, "短信验证码发送频率太高"),
    AUTH_USER_EXCEPTION(20000,"注册用户信息不合法"),
    AUTH_LOGIN_EXCEPTION(20000,"用户名或密码错误"),
    MEM_USER_PHONE_EXIST(30000, "用户手机号已存在"),
    MEM_USER_NAME_EXIST(30001, "用户名已存在");

    @Getter
    private Integer code;
    @Getter
    private String msg;

    BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
