package com.example.forum_hitsumabushi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 文字制限をチェックするバリデーション
@Target({ ElementType.FIELD })
@Constraint(validatedBy = CharacterLimitValid.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CharacterLimit {
    // デフォルトメッセージ
    String message() default "指定の条件で入力してください";

    // 文字数の判定
    int min() default 0;
    int max() default 100;

    // 入力文字の判定
    String regexp() default "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
