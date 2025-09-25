package com.example.forum_hitsumabushi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class LikeResponse {
    private final String targetType;
    private final Integer targetId;
    private final int LikeCount;
    private final String message;
    private final LocalDateTime timestamp;
}
