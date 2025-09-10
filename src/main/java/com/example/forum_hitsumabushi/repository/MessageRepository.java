package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.Message;
import com.example.forum_hitsumabushi.service.dto.UserMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("""
        SELECT new com.example.forum_hitsumabushi.service.dto.UserMessage(
            message.id,
            message.title,
            message.text,
            message.category,
            message.createdDate,
            message.updatedDate,
            user.id,
            user.account,
            user.name,
            user.branch.id,
            user.department.id
        )
        FROM Message message
        JOIN message.user user
        WHERE message.createdDate BETWEEN :start AND :end
        AND ((:category IS NULL OR :category = '') OR message.category LIKE CONCAT('%', :category, '%')) 
        ORDER BY message.createdDate DESC
    """)
    Page<UserMessage> findAllUserMessages(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("category") String category,
            Pageable pageable
    );
}
