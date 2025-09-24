package com.example.forum_hitsumabushi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

//クラス内のすべてのフィールドを引数に取るコンストラクタを自動生成
@AllArgsConstructor
@Getter
//エラーメッセージをJSONとして返すためのDTO
public class ErrorResponse {
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
}
