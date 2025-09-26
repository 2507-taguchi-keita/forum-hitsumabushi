package com.example.forum_hitsumabushi.controller;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.exception.MessageApiException;
import com.example.forum_hitsumabushi.service.LikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("/likes")
    public ResponseEntity<?> addLike(
            @RequestParam String targetType,
            @RequestParam Integer targetId,
            HttpSession session) {
        // セッションから loginUser を取得
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new MessageApiException("ログインしていません");
        }
        Integer userId = loginUser.getId();
        int count = likeService.addLike(targetType, targetId, userId);
        System.out.println("Like登録 target=" + targetType + " id=" + targetId + " userId=" + userId);
        Map<String, Object> response = Map.of(
                "targetType", targetType,
                "targetId", targetId,
                "likeCount", count
        );
        // 登録成功時にWebSocket通知を飛ばす
        messagingTemplate.convertAndSend("/topic/likes", response);
        System.out.println("WebSocket通知送信: " + response);
        return ResponseEntity.ok(response);
    }

    //いいね数取得
    @GetMapping("/likes")
    public ResponseEntity<?> getLikeCount(
            @RequestParam String targetType,
            @RequestParam Integer targetId) {

        int count = likeService.getLikeCount(targetType, targetId);

        Map<String, Object> response = Map.of(
                "targetType", targetType,
                "targetId", targetId,
                "likeCount", count
        );

        return ResponseEntity.ok(response);
    }
}
