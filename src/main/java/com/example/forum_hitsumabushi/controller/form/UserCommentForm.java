package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserCommentForm {

    private Integer id;
    private String account;
    private String name;
    private Integer branchId;
    private Integer userId;
    private Integer messageId;
    private String text;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
