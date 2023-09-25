package com.example.demo.repository;

import com.example.demo.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "INSERT INTO Message (sender, context) SELECT :sender, " +
            ":context FROM DUAL WHERE NOT EXISTS " +
            "(SELECT id FROM Message WHERE sender=:sender AND context=:context)", nativeQuery = true)
    Boolean insertChatMessage(String sender ,String context);

}
