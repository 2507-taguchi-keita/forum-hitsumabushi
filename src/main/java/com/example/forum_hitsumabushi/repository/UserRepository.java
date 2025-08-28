package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByAccountOrderByUpdateddateDesc(String account);
    boolean existsByAccountAndBranchIdAndDepartmentId(String account, int i, int i1);
}
