package com.example.forum_hitsumabushi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 改行含めた文字数をチェックするバリデーション
@Target({ ElementType.FIELD })
@Constraint(validatedBy = CharacterLimitValid.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CharacterLimit {
    String message() default "指定の文字数以内で入力してください";
    int limit() default 100;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
