package com.wushiyuan.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

/**
 * @ClassName Res
 * @Info 测试
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/20 9:25
 * @Version
 **/
@ToString
public class Res<T> extends HashMap<String, Object> {
    @Setter @Getter
    private T data;

    public Res() {
        put("code", 0);
        put("msg", "success");
    }

    public static Res ok() {
        Res<Object> objectRes = new Res<>();
        return objectRes;
    }

}
