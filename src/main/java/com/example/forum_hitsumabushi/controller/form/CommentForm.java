package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private Integer id;
    private String text;
    private Integer userId;
    private Integer messageId;
    private Date createdDate;
    private Date updatedDate;
}
