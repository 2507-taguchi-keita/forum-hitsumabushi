package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.User;
import com.example.forum_hitsumabushi.service.dto.UserBranchDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
        SELECT new com.example.forum_hitsumabushi.service.dto.UserBranchDepartment(
            user.id,
            user.account,
            user.name,
            user.branch.id,
            user.department.id,
            user.isStopped,
            user.createdDate,
            user.updatedDate,
            branch.name,
            department.name
        )
        FROM User user
        JOIN user.branch branch
        JOIN user.department department
        ORDER BY user.createdDate DESC
    """)
    List<UserBranchDepartment> findAllUserBranchDepartment();

    @Query("SELECT u.password FROM User u WHERE u.id = :id")
    String findPasswordById(@Param("id") int id);

    List<User> findByAccountAndPassword(String account, String password);

    // ユーザー登録時のアカウント重複チェック
    int countByAccount(String account);
    // ユーザー編集時のアカウント重複チェック
    int countByAccountAndIdNot(String account, int id);

    boolean existsByAccountAndBranchIdAndDepartmentId(String account, int i, int i1);
}
