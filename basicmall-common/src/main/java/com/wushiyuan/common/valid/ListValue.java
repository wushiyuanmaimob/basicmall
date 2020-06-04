package com.wushiyuan.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {ListValueConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ListValue {
    //命中校验规则后的提示信息
    String message() default "{com.wushiyuan.common.valid.ListValue.message}";
    //分组校验
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};

    int[] vals() default {  };
}
