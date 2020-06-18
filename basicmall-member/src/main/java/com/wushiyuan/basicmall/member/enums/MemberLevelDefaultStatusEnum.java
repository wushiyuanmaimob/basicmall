package com.wushiyuan.basicmall.member.enums;

import lombok.Getter;

public enum MemberLevelDefaultStatusEnum {

    YES(1, "是默认等级"),
    NO(0, "不是默认等级");

    @Getter
    private Integer code;
    @Getter
    private String msg;

    MemberLevelDefaultStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
