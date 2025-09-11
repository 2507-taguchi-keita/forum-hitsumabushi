package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.utils.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;
    //createdDateを〇分前などに変換し、文字列を保持するための専用フィールドとして使用
    private String createdRelative;
    private LocalDateTime updatedDate;
    //表示頻度や使い方の違いでRelativeフィールドを持たせるかどうかが変わる
    private LocalDateTime lastLoginAt;
    private String lastLoginRelative;
}
