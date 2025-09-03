package com.example.forum_hitsumabushi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// 文字制限をチェックするバリデーション
public class CharacterLimitValid implements ConstraintValidator<CharacterLimit, String> {

    private int min;
    private int max;
    private String regexp;

    @Override
    public void initialize(CharacterLimit constraintAnnotation) {
        // アノテーション側で設定した値を受け取る
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 改行文字を空白に置き換え
        String characterCountCheck = value.replaceAll("\\r?\\n", "");

        // 文字数の判定
        if (characterCountCheck.length() < min && characterCountCheck.length() > max) {
            return false;
        }

        // 入力文字の判定
        if (!regexp.isEmpty() && !value.matches(regexp)) {
            return false;
        }

        // 条件を満たした場合、trueを返す
        return true;
    }
}
