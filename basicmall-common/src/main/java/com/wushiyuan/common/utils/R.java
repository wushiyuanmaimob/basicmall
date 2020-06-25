/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.wushiyuan.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
@ToString
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private Integer code = 0;
	@Getter @Setter
	private String msg = "success";

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		
		
		this.data = data;
	}

	public <T> R() { }

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.setMsg(msg);
		return r;
	}

	public static  R ok() {
		return new R<>();
	}

	public R put(String s, Object o) {
		HashMap<Object, Object> map = new HashMap<>();
		map.put(s, o);

		return this;
	}

	public Object get(String s) {
		return null;
	}

}
