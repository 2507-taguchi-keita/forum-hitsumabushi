package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.repository.UserRepository;
import com.example.forum_hitsumabushi.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //アカウントを検索
    public List<UserForm> findByAccount(String account){
        List<User> results = userRepository.findByAccountOrderByUpdateddateDesc(account);
        return setUserForm(results);
    }

    //EntityからFormへ詰め替え
    private List<UserForm> setUserForm(List<User> results){
        List<UserForm> users = new ArrayList<>();
        for (User result : results) {
            UserForm user = new UserForm();
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsstopped(result.getIsstopped());
            user.setCreateddate(result.getCreateddate());
            user.setUpdateddate(result.getUpdateddate());
            users.add(user);
        }
        return users;
    }

    //総務人事かどうかを判定
    public boolean isSoumuJinji(String account){
        return userRepository.existsByAccountAndBranchIdAndDepartmentId(account, 1, 2);
    }
}
