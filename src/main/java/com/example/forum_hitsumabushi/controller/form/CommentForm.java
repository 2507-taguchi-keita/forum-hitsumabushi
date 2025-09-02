package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.validation.CharacterLimit;
import com.example.forum_hitsumabushi.validation.NotOnlyWhitespace;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentForm {

    private Integer id;

    @NotOnlyWhitespace(message = "メッセージを入力してください")
    @CharacterLimit(limit = 500, message = "500文字以内で入力してください")
    private String text;
    private Integer userId;
    private Integer messageId;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
