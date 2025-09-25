package com.example.forum_hitsumabushi.handler;

import com.example.forum_hitsumabushi.exception.MessageApiException;
import com.example.forum_hitsumabushi.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    //指定した例外(MessageApiException)が発生した時に呼ばれる。
    @ExceptionHandler(MessageApiException.class)
    //戻り値でHTTPレスポンス全体を返す。ErrorResponseはJSONに変換されて返る。
    public ResponseEntity<ErrorResponse> handleMessageApiException(MessageApiException ex){
        //実際に返すレスポンスを作成する。例外で渡されたエラーメッセージ、数値(400)、エラーが起きた時刻を詰める。
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        //レスポンスを組み立てる。HTTP400として返す。
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
