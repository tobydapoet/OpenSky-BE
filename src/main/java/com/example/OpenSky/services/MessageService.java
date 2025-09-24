package com.example.OpenSky.services;

import com.example.OpenSky.entities.Message;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.repositories.MessageRepository;
import com.example.OpenSky.requests.Message.MessageCreateRequest;
import com.example.OpenSky.requests.Message.MessageUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public Message findById(String id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public Page<Message> findConversation(String user, String otherUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findConversation(user, otherUser, pageable);
    }

    public Message create(MessageCreateRequest req) {
        Message message = new Message();
        User sender = userService.findById(req.getSenderId());
        User receiver = userService.findById(req.getReceiverId());
        message.setSenderUser(sender);
        message.setReceiverUser(receiver);
        message.setMessage(req.getMessage());
        Message savedMessage =  messageRepository.save(message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverUser().getId(),"/topic/messages", savedMessage);
        simpMessagingTemplate.convertAndSendToUser(message.getSenderUser().getId(),"/topic/messages", savedMessage);
        return savedMessage;
    }

    public Message update(String id,MessageUpdateRequest req) {
        Message message = findById(id);
        message.setMessage(req.getMessage());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverUser().getId(), "/topic/messages", message);
        return messageRepository.save(message);
    }

    public Message isReadMessage(String id) {
        Message message = findById(id);
        message.setReaded(true);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverUser().getId(), "/topic/messages", message);
        return messageRepository.save(message);
    }

    public void delete(String id) {
        Message message = findById(id);
        messageRepository.delete(message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverUser().getId(), "/topic/messages", message);

    }
}
