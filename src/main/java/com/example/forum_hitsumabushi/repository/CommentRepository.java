package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.Comment;
import com.example.forum_hitsumabushi.service.dto.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("""
    SELECT new com.example.forum_hitsumabushi.service.dto.UserComment(
        comment.id,
        user.account,
        user.name,
        user.branch.id,
        user.id,
        comment.messageId,
        comment.text,
        comment.createdDate,
        comment.updatedDate
    )
    FROM Comment comment
    JOIN comment.user user
    ORDER BY comment.createdDate ASC
""")
    List<UserComment> findAllUserComments();
}
