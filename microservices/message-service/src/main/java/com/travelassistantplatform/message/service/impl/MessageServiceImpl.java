package com.travelassistantplatform.message.service.impl;

import com.travelassistantplatform.message.enums.ResultEnum;
import com.travelassistantplatform.message.exception.NotFoundException;
import com.travelassistantplatform.message.exception.PersistenceFailureException;
import com.travelassistantplatform.message.model.Message;
import com.travelassistantplatform.message.repository.MessageRepository;
import com.travelassistantplatform.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        Message savedMessage = messageRepository.save(message);
        if (savedMessage == null){
            throw new PersistenceFailureException(ResultEnum.MESSAGE_SAVING_FAILED);
        }
        return savedMessage;
    }

    @Override
    public Message findOneByMessageId(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findMessageByMessageId(messageId);
        if (! messageOptional.isPresent()) {
            log.error("[MessageServiceImpl::findOneByMessageId] Message not found. destinationId:{}", messageId);
            throw new NotFoundException(ResultEnum.MESSAGE_NOT_FOUND);
        }
        return messageOptional.get();
    }

    @Override
    public List<Message> findMessagesByUserId(Long userId) {
        List<Message> retrievedMessages = messageRepository.findMessagesByUserIdOrderByCreatedAtDesc(userId);
        if (retrievedMessages.isEmpty()){
            log.error("[MessageServiceImpl::findMessagesByUserId] User id not found. destinationId:{}", userId);
            throw new NotFoundException(ResultEnum.USER_HAS_NO_CHAT_HISTORY);
        }
        return retrievedMessages;
    }

    @Override
    public List<Message> findMessagesByChatId(Long chatId) {
        List<Message> retrievedMessages = messageRepository.findMessagesByChatIdOrderByCreatedAtDesc(chatId);
        if (retrievedMessages.isEmpty()){
            log.error("[MessageServiceImpl::findMessagesByChatId] Chat id not found. destinationId:{}", chatId);
            throw new NotFoundException(ResultEnum.CHAT_NOT_FOUND);
        }
        return retrievedMessages;
    }

    @Override
    public List<Long> findChatIdDistinctByUserId(Long userId) {
        List<Message> retrievedMessages = messageRepository.findDistinctByUserIdOrderByCreatedAtDesc(userId);
        if (retrievedMessages.isEmpty()) {
            throw new NotFoundException(ResultEnum.USER_HAS_NO_CHAT_HISTORY);
        }

        /** Set<Long> to eliminate duplicate chatIds */
        Set<Long> retrievedChatIds = new LinkedHashSet<>();
        for (Message m:retrievedMessages) {
            retrievedChatIds.add(m.getChatId());
        }

        /** prepare return results */
        List<Long> distinctChatIdList = new ArrayList<>();
        for (Long chatId: retrievedChatIds) {
            distinctChatIdList.add(chatId);
        }
        return distinctChatIdList;
    }
}
