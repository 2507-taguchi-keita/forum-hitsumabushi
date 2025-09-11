package com.example.forum_hitsumabushi.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    //DateTimeFormatter＝日付時刻の書式を定義するクラス　　ofPattern＝書式を自分で決められるメソッド
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    //toRelativeメソッド＝最終ログイン日時・投稿日時等を相対的な表現（〇秒前）に変換するためのもの
    public static String toRelative(LocalDateTime createdAt) {
        //ログインした事が無い人には未ログインを返す
        if (createdAt == null) {
            return "未ログイン";
        }
        LocalDateTime now = LocalDateTime.now();
        //ログインした時刻との差分をDurationで計算
        Duration duration = Duration.between(createdAt, now);

        //差分を秒単位で取り出す処理
        long seconds = duration.getSeconds();
        //FORMATTERで日時を文字列へ変換。ここでは相対的な表示だけじゃなく絶対日時（2025/09/11）などを使う準備をする
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
