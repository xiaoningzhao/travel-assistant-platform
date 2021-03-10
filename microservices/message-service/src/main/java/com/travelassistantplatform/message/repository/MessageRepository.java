package com.travelassistantplatform.message.repository;

import com.travelassistantplatform.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findMessageByMessageId(Long messageId);
    List<Message> findMessagesByUserIdOrderByCreatedAtDesc(Long userId);
    List<Message> findMessagesByChatIdOrderByCreatedAtDesc(Long chatId);
    List<Message> findDistinctByUserIdOrderByCreatedAtDesc(Long userId);
}
