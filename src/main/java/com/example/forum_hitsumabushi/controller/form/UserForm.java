package com.example.forum_hitsumabushi.controller.form;

import com.example.forum_hitsumabushi.utils.DateTimeUtil;
import com.example.forum_hitsumabushi.validation.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserForm {

    // users
    private Integer id;

    @NotOnlyWhitespace(message = "アカウント名を入力してください", groups = AccountNotWhitespace.class)
    @CharacterLimit(min = 6, max = 20, regexp = "^[a-zA-Z0-9]+$", message = "アカウント名は半角英数字かつ6文字以上20文字以下で入力してください", groups = AccountCharaLimit.class)
    private String account;

    @NotOnlyWhitespace(message = "パスワードを入力してください", groups = PasswordNotWhitespace.class)
    @CharacterLimit(min = 6, max = 20, regexp = "^[\\x20-\\x7E]+$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = PasswordCharaLimit.class)
    private String password;

    @NotOnlyWhitespace(message = "氏名を入力してください")
    @CharacterLimit(max = 10, message = "氏名は10文字以下で入力してください")
    private String name;

    @NotNull(message = "支社を選択してください")
    private Integer branchId;

    @NotNull(message = "部署を選択してください")
    private Integer departmentId;

    private Integer isStopped;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // branches
    private String branchName;

    // departments
    private String departmentName;

    //最終ログイン日時を表示するため
    private LocalDateTime lastLoginAt;
    private String lastLoginRelative;

    public void setLastLoginAt(LocalDateTime lastLoginAt){
        this.lastLoginAt = lastLoginAt;
        this.lastLoginRelative = DateTimeUtil.toRelative(lastLoginAt);
    }
}
