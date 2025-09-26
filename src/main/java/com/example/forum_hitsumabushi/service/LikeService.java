package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.exception.MessageApiException;
import com.example.forum_hitsumabushi.repository.CommentRepository;
import com.example.forum_hitsumabushi.repository.LikeRepository;
import com.example.forum_hitsumabushi.repository.MessageRepository;
import com.example.forum_hitsumabushi.repository.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    CommentRepository commentRepository;

    //いいねを登録
    public int addLike(String targetType, Integer targetId, Integer userId) {
        // 存在チェック
        if (targetType.equals("message") && !messageRepository.existsById(targetId)) {
            throw new MessageApiException("対象の投稿が存在しません");
        }
        if (targetType.equals("comment") && !commentRepository.existsById(targetId)) {
            throw new MessageApiException("対象のコメントが存在しません");
        }
        // 重複チェック
        if (likeRepository.existsByTargetTypeAndTargetIdAndUserId(targetType, targetId, userId)) {
            throw new MessageApiException("すでにいいね済みです");
        }

        // 登録
        Like like = new Like();
        like.setTargetType(targetType);
        like.setTargetId(targetId);
        like.setUserId(userId);
        like.setCreatedDate(LocalDateTime.now());
        likeRepository.save(like);

        return likeRepository.countByTargetTypeAndTargetId(targetType, targetId);
    }

    // いいね数取得
    public int getLikeCount(String targetType, Integer targetId) {
        return likeRepository.countByTargetTypeAndTargetId(targetType, targetId);
    }
}
