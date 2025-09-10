package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.CommentForm;
import com.example.forum_hitsumabushi.controller.form.UserCommentForm;
import com.example.forum_hitsumabushi.repository.CommentRepository;
import com.example.forum_hitsumabushi.repository.MessageRepository;
import com.example.forum_hitsumabushi.repository.UserRepository;
import com.example.forum_hitsumabushi.repository.entity.Comment;
import com.example.forum_hitsumabushi.repository.entity.Message;
import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.dto.UserComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    //返信コメント全件取得
    public List<UserCommentForm> findAllUserComment() {
        List<UserComment> results = commentRepository.findAllUserComments();
        return setCommentForm(results);
    }

    //返信コメント全件取得処理(EntityからFormへ詰め替え)
    private List<UserCommentForm> setCommentForm(List<UserComment> results){
        List<UserCommentForm> userComments = new ArrayList<>();
        for (UserComment result : results) {
            UserCommentForm userComment = new UserCommentForm();
            userComment.setId(result.getId());
            userComment.setAccount(result.getAccount());
            userComment.setName(result.getName());
            userComment.setBranchId(result.getBranchId());
            userComment.setUserId(result.getUserId());
            userComment.setMessageId(result.getMessageId());
            userComment.setText(result.getText());
            userComment.setCreatedDate(result.getCreatedDate());
            userComment.setUpdatedDate(result.getUpdatedDate());
            userComment.setLastLoginAt(result.getLastLoginAt());
            userComments.add(userComment);
        }
        return userComments;
    }

    public void addComment(Integer messageId, CommentForm commentForm, Integer userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません id: " + userId));
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new IllegalArgumentException("投稿が見つかりません id: " + messageId));

            Comment comment = new Comment();
            comment.setMessageId(messageId);
            comment.setText(commentForm.getText());
            comment.setUser(user);
            comment.setCreatedDate(LocalDateTime.now());
            comment.setUpdatedDate(LocalDateTime.now());

            commentRepository.save(comment);
    }

    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment findCommentById(Integer messageId) {
        return commentRepository.findById(messageId).orElse(null);
    }
}
