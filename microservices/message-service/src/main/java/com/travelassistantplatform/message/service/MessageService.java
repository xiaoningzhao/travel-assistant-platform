package com.travelassistantplatform.message.service;

import com.travelassistantplatform.message.model.Message;

import java.util.List;

public interface MessageService {
    /** save a message record to database */
    Message save(Message message);

    /** find a message by message id */
    Message findOneByMessageId(Long messageId);

    /** find all messages by user id */
    List<Message> findMessagesByUserId(Long userId);

    /** find all messages by chat id */
    List<Message> findMessagesByChatId(Long chatId);

    /** find distinct chatId by userId */
    List<Long> findChatIdDistinctByUserId(Long userId);
}
