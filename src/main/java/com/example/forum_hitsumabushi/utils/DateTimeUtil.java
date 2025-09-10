package com.example.forum_hitsumabushi.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String toRelative(LocalDateTime createdAt) {
        if (createdAt == null) {
            return "未ログイン";
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long seconds = duration.getSeconds();
        String absolute = createdAt.format(FORMATTER);

        if (seconds < 1) {
            return "たった今" + "（" + absolute + "）";
        } else if (seconds < 60) {
            return seconds + "秒前" + "（" + absolute + "）";
        } else if (seconds < 3600) {
            return (seconds / 60) + "分前" + "（" + absolute + "）";
        } else if (seconds < 86400) {
            return (seconds / 3600) + "時間前" + "（" + absolute + "）";
        } else if (seconds < 604800) {
            return (seconds / 86400) + "日前" + "（" + absolute + "）";
        } else {
            return absolute;
        }
    }
}
