package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.validation.AccountNotWhitespace;
import com.example.forum_hitsumabushi.validation.CharacterLimit;
import com.example.forum_hitsumabushi.validation.NotOnlyWhitespace;
import com.example.forum_hitsumabushi.validation.PasswordNotWhitespace;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserForm {

    // users
    private Integer id;

    @NotOnlyWhitespace(message = "アカウントを入力してください", groups = AccountNotWhitespace.class)
    @CharacterLimit(min = 6, max = 20, regexp = "^[a-zA-Z0-9]+$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください", groups = AccountNotWhitespace.class)
    private String account;

    @NotOnlyWhitespace(message = "パスワードを入力してください", groups = PasswordNotWhitespace.class)
    @CharacterLimit(min = 6, max = 20, regexp = "^[\\x20-\\x7E]+$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = PasswordNotWhitespace.class)
    private String password;

    @NotOnlyWhitespace(message = "氏名を入力してください")
    @CharacterLimit(max = 10, message = "氏名は10文字以下で入力してください")
    private String name;

    @NotNull(message = "支社を選択してください")
    private Integer branchId;

    @NotNull(message = "部署を選択してください")
    private Integer departmentId;

    private Integer isStopped;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    // branches
    private String branchName;

    // departments
    private String departmentName;
}
