package com.example.forum_hitsumabushi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// 改行含めた文字数をチェックするバリデーション
public class CharacterLimitValid implements ConstraintValidator<CharacterLimit, String> {

    private int limit;

    @Override
    public void initialize(CharacterLimit constraintAnnotation) {
        // アノテーション側で設定したlimit値を受け取る
        this.limit = constraintAnnotation.limit();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 改行文字を空白に置き換え
        String characterCountCheck = value.replaceAll("\\r?\\n", "");
        return characterCountCheck.length() <= limit;
    }
}
