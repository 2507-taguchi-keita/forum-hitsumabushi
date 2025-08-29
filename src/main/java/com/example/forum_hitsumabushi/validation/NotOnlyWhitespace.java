package com.example.forum_hitsumabushi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 空白のみ、改行のみ、スペースのみの入力チェックをするバリデーション。
// @NotBlankではスペースのみが判定できなかった為、カスタムアノテーションを作成。
@Target({ ElementType.FIELD })
@Constraint(validatedBy = NotOnlyWhitespaceValid.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotOnlyWhitespace {
    String message() default "入力してください";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
