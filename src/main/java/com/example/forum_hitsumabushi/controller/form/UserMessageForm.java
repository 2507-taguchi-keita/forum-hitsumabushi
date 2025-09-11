package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.utils.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserMessageForm {

    private Integer id;
    private String account;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer userId;
    private String title;
    private String text;
    private String category;
    private LocalDateTime createdDate;
    private String createdRelative;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLoginAt;
    private String lastLoginRelative;
}
