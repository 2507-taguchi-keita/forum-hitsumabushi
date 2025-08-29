package com.example.forum_hitsumabushi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

// 暗号化する為のクラス
public class CipherUtil {
    public static String encrypt(String target) {
        try {
            // SHA-256でハッシュ化
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(target.getBytes());
            return Base64.encodeBase64URLSafeString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
