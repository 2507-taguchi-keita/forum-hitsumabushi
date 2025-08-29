package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.UserMessageForm;
import com.example.forum_hitsumabushi.repository.MessageRepository;
import com.example.forum_hitsumabushi.service.dto.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    //投稿全件取得処理
    public List<UserMessageForm> findAllUserMessages(){
        List<UserMessage> results = messageRepository.findAllUserMessages();
        System.out.println("レコードの取得: " + results.size());
        for (UserMessage m : results) {
            System.out.println("id=" + m.getId()
                    + ", title=" + m.getTitle()
                    + ", text=" + m.getText()
                    + ", account=" + m.getAccount());
        }
        return setMessageForm(results);
    }

    //投稿全件取得処理(EntityからFormへ詰め替え)
    private List<UserMessageForm> setMessageForm(List<UserMessage> results){
        List<UserMessageForm> userComments = new ArrayList<>();
        for (UserMessage result : results) {
            UserMessageForm userMessage = new UserMessageForm();
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
}
