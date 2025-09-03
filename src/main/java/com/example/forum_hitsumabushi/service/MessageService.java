package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.MessageForm;
import com.example.forum_hitsumabushi.controller.form.UserMessageForm;
import com.example.forum_hitsumabushi.repository.CommentRepository;
import com.example.forum_hitsumabushi.repository.MessageRepository;
import com.example.forum_hitsumabushi.repository.UserRepository;
import com.example.forum_hitsumabushi.repository.entity.Message;
import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.dto.UserMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<UserMessageForm> findAllUserMessages(){
        List<UserMessage> results = messageRepository.findAllUserMessages();
        return setMessageForm(results);
    }

    //投稿全件取得処理(EntityからFormへ詰め替え)
    private List<UserMessageForm> setMessageForm(List<UserMessage> results){
        List<UserMessageForm> userComments = new ArrayList<>();
        for (UserMessage result : results) {
            UserMessageForm userMessage = new UserMessageForm();

            userMessage.setId(result.getId());
            userMessage.setAccount(result.getAccount());
            userMessage.setName(result.getName());
            userMessage.setBranchId(result.getBranchId());
            userMessage.setDepartmentId(result.getDepartmentId());
            userMessage.setUserId(result.getUserId());
            userMessage.setTitle(result.getTitle());
            userMessage.setText(result.getText());
            userMessage.setCategory(result.getCategory());
            userMessage.setCreatedDate(result.getCreatedDate());
            userMessage.setUpdatedDate(result.getUpdatedDate());
            userComments.add(userMessage);
        }
        return userComments;
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
        message.setCreatedDate(reqMessage.getCreatedDate());
        message.setUpdatedDate(reqMessage.getUpdatedDate());
        return message;
    }

    // 投稿を削除する処理
    @Transactional
    public void deleteMessage(Integer id) {
        //commentRepository.deleteByContentId(id);
        messageRepository.deleteById(id);
    }
}
