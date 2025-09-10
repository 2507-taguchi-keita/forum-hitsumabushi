package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.*;
import com.example.forum_hitsumabushi.repository.CommentRepository;
import com.example.forum_hitsumabushi.repository.MessageRepository;
import com.example.forum_hitsumabushi.repository.UserRepository;
import com.example.forum_hitsumabushi.repository.entity.Comment;
import com.example.forum_hitsumabushi.repository.entity.Message;
import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.dto.FilterDto;
import com.example.forum_hitsumabushi.service.dto.UserComment;
import com.example.forum_hitsumabushi.service.dto.UserMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    CommentRepository commentRepository;

    //投稿全件取得処理
    public Page<UserMessageForm> findAllUserMessages(FilterDto filterDto, int page){
        LocalDateTime start = filterDto.getStartDateTime();
        LocalDateTime end = filterDto.getEndDateTime();
        String category = filterDto.getCategory();
        Pageable pageable = PageRequest.of(page, 10);

        Page<UserMessage> results = messageRepository.findAllUserMessages(start, end, category, pageable);
//        Page<UserMessageForm> userMessageList = setMessageForm(results);
        Page<UserMessageForm> userMessageList = results.map(this::setMessageForm);
        return userMessageList;
    }

    //投稿全件取得処理(EntityからFormへ詰め替え)
//    private List<UserMessageForm> setMessageForm(List<UserMessage> results){
//        List<UserMessageForm> userMessages = new ArrayList<>();
//        for (UserMessage result : results) {
//            UserMessageForm userMessage = new UserMessageForm();
//
//            userMessage.setId(result.getId());
//            userMessage.setAccount(result.getAccount());
//            userMessage.setName(result.getName());
//            userMessage.setBranchId(result.getBranchId());
//            userMessage.setDepartmentId(result.getDepartmentId());
//            userMessage.setUserId(result.getUserId());
//            userMessage.setTitle(result.getTitle());
//            userMessage.setText(result.getText());
//            userMessage.setCategory(result.getCategory());
//            userMessage.setCreatedDate(result.getCreatedDate());
//            userMessage.setUpdatedDate(result.getUpdatedDate());
//            userMessages.add(userMessage);
//        }
//        return userMessages;
//    }

    //投稿全件取得処理(EntityからFormへ詰め替え)
    private UserMessageForm setMessageForm(UserMessage results){
        UserMessageForm userMessages = new UserMessageForm();

        userMessages.setId(results.getId());
        userMessages.setAccount(results.getAccount());
        userMessages.setName(results.getName());
        userMessages.setBranchId(results.getBranchId());
        userMessages.setDepartmentId(results.getDepartmentId());
        userMessages.setUserId(results.getUserId());
        userMessages.setTitle(results.getTitle());
        userMessages.setText(results.getText());
        userMessages.setCategory(results.getCategory());
        userMessages.setCreatedDate(results.getCreatedDate());
        userMessages.setUpdatedDate(results.getUpdatedDate());

        return userMessages;
    }

    // 投稿を追加する処理
    public void saveMessage(MessageForm reqMessage) {
        Message saveMessage = setMessageEntity(reqMessage);
        messageRepository.save(saveMessage);
    }

    private Message setMessageEntity(MessageForm reqMessage) {
        Message message = new Message();

        // Entity(Message)がuser_idカラムをUser型で使ってるので型を変えるための処理
        Integer userId = reqMessage.getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            message.setUser(user);
        } else {
            // ユーザーが見つからなかった場合の例外処理
            throw new IllegalArgumentException("ユーザーが見つかりません。id: " + userId);
        }

        // データセット
        message.setId(reqMessage.getId());
        message.setTitle(reqMessage.getTitle());
        message.setText(reqMessage.getText());
        message.setCategory(reqMessage.getCategory());
        if (message.getCreatedDate() == null) {
            message.setCreatedDate(LocalDateTime.now());
        }
        message.setUpdatedDate(LocalDateTime.now());
        return message;
    }

    // 投稿を削除する処理
    @Transactional
    public void deleteMessage(Integer id) {
        commentRepository.deleteByMessageId(id);
        messageRepository.deleteById(id);
    }

    // 投稿の削除権限のチェック
    public boolean deleteMessageCheck(UserForm login, UserForm post) {
        int loginUser = login.getId();
        int loginBranch = login.getBranchId();
        int loginDepartment = login.getDepartmentId();
        int postUser = post.getId();
        int postBranch = post.getBranchId();
        int postDepartment = post.getDepartmentId();

        // 投稿の削除が可能な条件：下記①～③のいずれかを満たしている場合
        // 　①ログインユーザーと投稿ユーザーが一致している場合
        // 　②ログインユーザーが本社情報管理部(ID：2)である場合
        // 　③下記3つの条件がすべて満たせている場合
        // 　　- ログインユーザーの支社と投稿ユーザーの支社が同じ
        // 　　- ログインユーザーは営業部(ID：3)
        // 　　- 投稿ユーザーは技術部(ID：4)

        if (loginUser == postUser) {
            return true;
        } else if ((loginBranch == 2) && (loginDepartment == 2)) {
            return true;
        } else if ((loginBranch == postBranch) && (loginDepartment == 3) && (postDepartment == 4)) {
            return true;
        } else {
            return false;
        }
    }

    public Message findMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }
}
