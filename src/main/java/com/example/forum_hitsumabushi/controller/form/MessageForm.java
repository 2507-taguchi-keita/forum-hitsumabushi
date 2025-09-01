package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.validation.CharacterLimit;
import com.example.forum_hitsumabushi.validation.NotOnlyWhitespace;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class MessageForm {

    private Integer id;

    @NotOnlyWhitespace(message = "件名を入力してください")
    @CharacterLimit(limit = 30, message = "件名は30文字以内で入力してください")
    private String title;

    @NotOnlyWhitespace(message = "本文を入力してください")
    @CharacterLimit(limit = 1000, message = "本文は1000文字以内で入力してください")
    private String text;

    @NotOnlyWhitespace(message = "カテゴリを入力してください")
    @CharacterLimit(limit = 10, message = "カテゴリは10文字以内で入力してください")
    private String category;

    private Integer userId;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
