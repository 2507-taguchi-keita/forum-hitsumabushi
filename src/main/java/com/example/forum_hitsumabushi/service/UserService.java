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

    public UserForm findUserLogin(UserForm userForm) {
        String account = userForm.getAccount();
        String encPassword = userForm.getPassword();
        //String encPassword = CipherUtil.encrypt(userForm.getPassword());

        List<User> results = userRepository.findByAccountAndPassword(account, encPassword);
        List<UserForm> user = setUserForm(results);

        if (user.isEmpty() || user.get(0).getIsStopped() == 1) {
            return null;
        } else if (user.size() >= 2) {
            throw new IllegalStateException("ユーザーが重複しています");
        } else {
            return user.get(0);
        }
    }

    //アカウントを検索
    public List<UserForm> findByAccount(String account){
        List<User> results = userRepository.findByAccountOrderByUpdatedDateDesc(account);
        return setUserForm(results);
    }

    //EntityからFormへ詰め替え
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);

            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsStopped(result.getIsStopped());
            user.setCreatedDate(result.getCreatedDate());
            user.setUpdatedDate(result.getUpdatedDate());
            users.add(user);
        }
        return users;
    }

    //総務人事かどうかを判定
    public boolean isSoumuJinji(String account){
        return userRepository.existsByAccountAndBranchIdAndDepartmentId(account, 1, 2);
    }
}
