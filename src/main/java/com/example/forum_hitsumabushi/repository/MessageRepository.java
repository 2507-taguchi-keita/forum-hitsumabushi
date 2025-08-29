package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.Message;
import com.example.forum_hitsumabushi.service.dto.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
            user.branchId,
            user.departmentId
        )
        FROM Message message
        JOIN message.user user
        ORDER BY message.createdDate DESC
    """)
    List<UserMessage> findAllUserMessages();
}
