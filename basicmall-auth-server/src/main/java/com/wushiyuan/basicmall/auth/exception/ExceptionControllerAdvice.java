package com.wushiyuan.basicmall.auth.exception;

import com.wushiyuan.common.exception.BizCodeEnum;
import com.wushiyuan.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.wushiyuan.basicmall.auth.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, String> msgMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            msgMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return R.error(BizCodeEnum.AUTH_USER_EXCEPTION.getCode(), BizCodeEnum.AUTH_USER_EXCEPTION.getMsg()).put("data", msgMap);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        return R.error().put("data", e.getMessage());
    }
}
