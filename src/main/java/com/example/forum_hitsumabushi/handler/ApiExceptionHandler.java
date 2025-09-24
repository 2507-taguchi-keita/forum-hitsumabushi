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
    @ExceptionHandler(MessageApiException.class)
    public ResponseEntity<ErrorResponse> handleMessageApiException(MessageApiException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
