package com.example.forum_hitsumabushi.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtil {
    public static String toRelative(LocalDateTime createdAt) {
        if (createdAt == null) {
            return "未ログイン";
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long seconds = duration.getSeconds();

        if (seconds < 1) {
            return "たった今";
        } else if (seconds < 60) {
            return seconds + "秒前";
        } else if (seconds < 3600) {
            return (seconds / 60) + "分前";
        } else if (seconds < 86400) {
            return (seconds / 3600) + "時間前";
        } else if (seconds < 604800) {
            return (seconds / 86400) + "日前";
        } else {
            return createdAt.toLocalDate().toString(); // 日付表示に切り替え
        }
    }
}
