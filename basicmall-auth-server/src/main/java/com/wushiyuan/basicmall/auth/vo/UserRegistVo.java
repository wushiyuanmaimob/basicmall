package com.wushiyuan.basicmall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegistVo {
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 16, message = "用户名长度为4-16个字符")
    private String name;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度为6-16个字符")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    @Length(min = 6, max = 16, message = "确认密码长度为6-16个字符")
    private String confirmPassword;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}", message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
