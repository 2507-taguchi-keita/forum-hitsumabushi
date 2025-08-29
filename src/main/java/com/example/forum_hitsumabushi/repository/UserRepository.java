package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByAccountAndPassword(String account, String password);
    List<User> findByAccountOrderByUpdateddateDesc(String account);
    boolean existsByAccountAndBranchIdAndDepartmentId(String account, int i, int i1);
}
