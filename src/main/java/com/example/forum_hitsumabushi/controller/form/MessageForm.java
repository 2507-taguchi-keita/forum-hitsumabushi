package com.example.forum_hitsumabushi.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageForm {

    private Integer id;
    private String title;
    private String text;
    private String category;
    private Integer userId;
    private Date createdDate;
    private Date updatedDate;
}
