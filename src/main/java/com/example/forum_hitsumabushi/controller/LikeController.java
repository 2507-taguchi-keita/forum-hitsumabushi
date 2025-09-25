package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.service.LikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<?> addLike(
            @RequestParam String targetType,
            @RequestParam Integer targetId,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId"); // セッションから取得

        int count = likeService.addLike(targetType, targetId, userId);

        // Long → Integer に変換して返す
        return ResponseEntity.ok(Map.of(
                "targetId", Math.toIntExact(targetId),
                "likeCount", count
        ));
    }

    // いいね数取得
    @GetMapping("/likes")
    public ResponseEntity<?> getLikeCount(
            @RequestParam String targetType,
            @RequestParam Integer targetId) {

        int count = likeService.getLikeCount(targetType, targetId);

        return ResponseEntity.ok(Map.of(
                "targetId", Math.toIntExact(targetId),
                "likeCount", count
        ));
    }
}
