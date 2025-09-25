package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    //重複チェックに対応
    boolean existsByTargetTypeAndTargetIdAndUserId(String targetType, Integer targetId, Integer userId);
    //いいね数を集計する際に使用
    int countByTargetTypeAndTargetId(String targetType, Integer targetId);
}
