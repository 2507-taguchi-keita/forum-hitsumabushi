package com.example.forum_hitsumabushi.exception;

public class MessageApiException extends RuntimeException {
    public MessageApiException(String message){
        //親クラス(RuntimeException)のコンストラクタにmessageを渡す。
        super(message);
    }
}
