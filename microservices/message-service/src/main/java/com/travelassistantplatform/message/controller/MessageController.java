package com.travelassistantplatform.message.controller;

import com.travelassistantplatform.message.model.Message;
import com.travelassistantplatform.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping(path = "/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /** save */
    @PostMapping(path = "/save", produces = "application/json")
    public ResponseEntity<Message> save(@RequestBody Message message){
        Message savedMessage = messageService.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    /** find a message by message id */
    @GetMapping(path = "/getOneByMessageId", produces = "application/json")
    public ResponseEntity<Message> findOneByMessageId(@RequestParam(value = "messageId") Long messageId){
        Message retrievedMessage = messageService.findOneByMessageId(messageId);
        return ResponseEntity.ok(retrievedMessage);
    }

    /** find all messages by user id */
    @GetMapping(path = "/getByUserId", produces = "application/json")
    public List<Message> findMessagesByUserId(@RequestParam(value = "userId") Long userId){
        return messageService.findMessagesByUserId(userId);
    }

    /** find all messages by chat id */
    @GetMapping(path = "/getByChatId", produces = "application/json")
    public List<Message> findMessagesByChatId(@RequestParam(value = "chatId") Long chatId){
        return messageService.findMessagesByChatId(chatId);
    }

    /** find distinct chatId by userId */
    @GetMapping(path = "/getUserChatIds", produces = "application/json")
    public List<Long> findChatIdDistinctByUserId(@RequestParam(value = "userId") Long userId){
        return messageService.findChatIdDistinctByUserId(userId);
    }
}
