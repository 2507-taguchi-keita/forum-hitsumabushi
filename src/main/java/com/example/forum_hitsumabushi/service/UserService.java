package com.example.forum_hitsumabushi.service;

import com.example.forum_hitsumabushi.controller.form.BranchForm;
import com.example.forum_hitsumabushi.controller.form.DepartmentForm;
import com.example.forum_hitsumabushi.controller.form.UserForm;
import com.example.forum_hitsumabushi.repository.BranchDepartmentRepository;
import com.example.forum_hitsumabushi.repository.BranchRepository;
import com.example.forum_hitsumabushi.repository.DepartmentRepository;
import com.example.forum_hitsumabushi.repository.UserRepository;
import com.example.forum_hitsumabushi.repository.entity.Branch;
import com.example.forum_hitsumabushi.repository.entity.Department;
import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.dto.UserBranchDepartment;
import com.example.forum_hitsumabushi.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    BranchDepartmentRepository branchdepartmentRepository;

    // ログインチェック
    public UserForm findUserLogin(UserForm userForm) {
        String account = userForm.getAccount();
//        String encPassword = userForm.getPassword();
        // パスワードの暗号化
        String encPassword = CipherUtil.encrypt(userForm.getPassword());

        List<User> results = userRepository.findByAccountAndPassword(account, encPassword);
        List<UserForm> user = setUser(results);

        if (user.isEmpty() || user.get(0).getIsStopped() == 1) {
            return null;
        } else if (user.size() >= 2) {
            throw new IllegalStateException("ユーザーが重複しています");
        } else {
            return user.get(0);
        }
    }

    // 支社と部署の組み合わせチェック
    public boolean findByBranchDepartment(int branchId, int departmentId){
        boolean checkResult = branchdepartmentRepository.findBranchDepartment(branchId, departmentId);
        return checkResult;
    }

    // アカウントチェック(取得結果が"0"ならtrueを返す＝重複なし)
    public boolean findByAccount(Integer id, String account){
        if (id == null) {
            // ユーザー登録時はアカウント名のみでのチェック
            int count = userRepository.countByAccount(account);
            return count == 0;
        } else {
            // ユーザー編集時はアカウント名＋idでのチェック（自身のidとの重複は許可する為）
            int count = userRepository.countByAccountAndIdNot(account, id);
            return count == 0;
        }
    }

    // ユーザー情報を取得（テーブル：users, branches, departments）
    public List<UserForm> findAllUser() {
        List<UserBranchDepartment> results = userRepository.findAllUserBranchDepartment();
        List<UserForm> user = setUserDtoForm(results);
        return user;
    }

    // 編集対象のユーザー情報を取得（テーブル：users）
    public UserForm editUser(Integer id) {
        User userResult = userRepository.findById(id).orElse(null);

        if (userResult == null) {
            return null;
        } else {
            List<User> results = new ArrayList<>();
            results.add(userResult);
            List<UserForm> users = setUser(results);
            return users.get(0);
        }
    }

    // 支社情報を取得（テーブル：branches）
    public List<BranchForm> findAllBranch() {
        List<Branch> results = branchRepository.findAll();
        List<BranchForm> branch = setBranch(results);
        return branch;
    }

    // 部署情報を取得（テーブル：departments）
    public List<DepartmentForm> findAllDepartment() {
        List<Department> results = departmentRepository.findAll();
        List<DepartmentForm> department = setDepartment(results);
        return department;
    }

    // ユーザー情報の更新（テーブル：users）
    public void saveUser(UserForm reqUser) {
        User saveUser = setUserFormEntity(reqUser);

        // ユーザー登録時はデフォルトで状態：0となるように条件分岐
        if (saveUser.getIsStopped() == null) {
            saveUser.setIsStopped(0);
        }
        userRepository.save(saveUser);
    }

    // -------------------------------
    // DtoからFormへ詰め替え（UserBranchDepartment → UserForm）
    private List<UserForm> setUserDtoForm(List<UserBranchDepartment> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            UserBranchDepartment result = results.get(i);

            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsStopped(result.getIsStopped());
            user.setCreatedDate(result.getCreatedDate());
            user.setUpdatedDate(result.getUpdatedDate());
            user.setBranchName(result.getBranchName());
            user.setDepartmentName(result.getDepartmentName());
            users.add(user);
        }
        return users;
    }

    // FormからEntityへ詰め替え（User → UserForm）
    private User setUserFormEntity(UserForm reqUser) {
        User user = new User();

        user.setId(reqUser.getId());
        user.setAccount(reqUser.getAccount());
        user.setName(reqUser.getName());
        user.setIsStopped(reqUser.getIsStopped());
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setLastLoginAt(reqUser.getLastLoginAt());

        // ユーザー編集時は「パスワードが空白＝更新しない」となるので、条件分岐でセットする値を使い分け
        if (reqUser.getPassword().isEmpty()) {
            String pass = userRepository.findPasswordById(reqUser.getId());
            user.setPassword(pass);
        } else {
            user.setPassword(reqUser.getPassword());
        }

        // Entity(User)がbranch_idカラムをBranch型で使ってるので型を変える処理後にセット
        Integer branchId = reqUser.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            user.setBranch(branch);
        } else {
            // ユーザーが見つからなかった場合の例外処理
            throw new IllegalArgumentException("ユーザーが見つかりません。id: " + branchId);
        }

        // Entity(User)がdepartment_idカラムをDepartment型で使ってるので型を変える処理後にセット
        Integer departmentId = reqUser.getDepartmentId();
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            user.setDepartment(department);
        } else {
            // ユーザーが見つからなかった場合の例外処理
            throw new IllegalArgumentException("ユーザーが見つかりません。id: " + departmentId);
        }

        return user;
    }

    // EntityからFormへ詰め替え（User → UserForm）
    private List<UserForm> setUser(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);

            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setName(result.getName());
            user.setBranchId(result.getBranch().getId());
            user.setDepartmentId(result.getDepartment().getId());
            user.setIsStopped(result.getIsStopped());
            user.setCreatedDate(result.getCreatedDate());
            user.setUpdatedDate(result.getUpdatedDate());
            user.setLastLoginAt(result.getLastLoginAt());
            users.add(user);
        }
        return users;
    }

    // EntityからFormへ詰め替え（Branch → BranchForm）
    private List<BranchForm> setBranch(List<Branch> results) {
        List<BranchForm> branches = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            BranchForm branch = new BranchForm();
            Branch result = results.get(i);

            branch.setId(result.getId());
            branch.setName(result.getName());
            branch.setCreatedDate(result.getCreatedDate());
            branch.setUpdatedDate(result.getUpdatedDate());
            branches.add(branch);
        }
        return branches;
    }

    // EntityからFormへ詰め替え（Department → DepartmentForm）
    private List<DepartmentForm> setDepartment(List<Department> results) {
        List<DepartmentForm> departments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            DepartmentForm department = new DepartmentForm();
            Department result = results.get(i);

            department.setId(result.getId());
            department.setName(result.getName());
            department.setCreatedDate(result.getCreatedDate());
            department.setUpdatedDate(result.getUpdatedDate());
            departments.add(department);
        }
        return departments;
    }

}
