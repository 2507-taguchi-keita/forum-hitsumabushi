package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.validation.NotOnlyWhitespace;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {

    private Integer id;

    @NotOnlyWhitespace(message = "アカウントを入力してください")
    private String account;

    @NotOnlyWhitespace(message = "パスワードを入力してください")
    private String password;

    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;
    private Date createddate;
    private Date updateddate;
}
