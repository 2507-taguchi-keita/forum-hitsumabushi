package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private Integer id;
    private String text;
    private Integer userId;
    private Integer messageId;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
