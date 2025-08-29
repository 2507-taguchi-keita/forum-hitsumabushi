package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.UserCommentForm;
import com.example.forum_hitsumabushi.repository.CommentRepository;
import com.example.forum_hitsumabushi.service.dto.UserComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //返信コメント全件取得
    public List<UserCommentForm> findAllComment() {
        List<UserComment> results = commentRepository.findAllUserComments();
        return setCommentForm(results);
    }

    //返信コメント全件取得処理(EntityからFormへ詰め替え)
    private List<UserCommentForm> setCommentForm(List<UserComment> results){
        List<UserCommentForm> userComments = new ArrayList<>();
        for (UserComment result : results) {
            UserCommentForm userComment = new UserCommentForm();
            userComment.setAccount(result.getAccount());
            userComment.setName(result.getName());
            userComment.setBranchId(result.getBranchId());
            userComment.setUserId(result.getUserId());
            userComment.setMessageId(result.getMessageId());
            userComment.setText(result.getText());
            userComment.setCreatedDate(result.getCreatedDate());
            userComment.setUpdatedDate(result.getUpdatedDate());
            userComments.add(userComment);
        }
        return userComments;
    }
}
