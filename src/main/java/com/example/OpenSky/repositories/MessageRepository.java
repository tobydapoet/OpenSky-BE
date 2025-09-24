package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Image;
import com.example.OpenSky.entities.Message;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message,String> {
    @Query("SELECT m FROM Message m " +
            "WHERE (m.senderUser = :user1 AND m.receiverUser = :user2) " +
            "   OR (m.senderUser = :user2 AND m.receiverUser = :user1) " +
            "ORDER BY m.createdAt DESC")
    Page<Message> findConversation(@Param("user1") String user1,
                                   @Param("user2") String user2,
                                   Pageable pageable);
}
