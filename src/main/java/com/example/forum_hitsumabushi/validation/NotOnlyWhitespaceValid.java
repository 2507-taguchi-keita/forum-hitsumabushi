package com.example.forum_hitsumabushi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotOnlyWhitespaceValid implements ConstraintValidator<NotOnlyWhitespace, String> {

    // 空白のみ、改行のみ、スペースのみをチェックするバリデーション。
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return !value.isBlank();
    }
}
