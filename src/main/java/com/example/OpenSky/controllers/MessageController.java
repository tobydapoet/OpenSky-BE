package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Message.MessageDto;
import com.example.OpenSky.entities.Message;
import com.example.OpenSky.requests.Message.MessageCreateRequest;
import com.example.OpenSky.requests.Message.MessageUpdateRequest;
import com.example.OpenSky.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Lấy tin nhắn theo id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public MessageDto getMessageById(@PathVariable String id) {
        Message message = messageService.findById(id);
        return MessageDto.fromEntity(message);
    }

    @Operation(summary = "Lấy tin nhắn theo cuộc hội thoại",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/conversation")
    public Object getConversation(
            @RequestParam String user,
            @RequestParam String otherUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return messageService.findConversation(user, otherUser, page, size);
    }

    @Operation(summary = "Tạo tin nhắn mới",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping
    public MessageDto createMessage(@RequestBody MessageCreateRequest request) {
        Message message = messageService.create(request);
        return MessageDto.fromEntity(message);
    }

    @Operation(summary = "Cập nhật tin nhắn",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping("/{id}")
    public MessageDto updateMessage(@PathVariable String id, @RequestBody MessageUpdateRequest request) {
        Message message = messageService.update(id, request);
        return MessageDto.fromEntity(message);

    }

    @Operation(summary = "Đánh dấu tin nhắn đã đọc",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping("/{id}/read")
    public MessageDto readMessage(@PathVariable String id) {
        Message message = messageService.isReadMessage(id);
        return MessageDto.fromEntity(message);

    }

    @Operation(summary = "Xóa tin nhắn",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable String id) {
        messageService.delete(id);
    }


    @MessageMapping("/send")
    @SendToUser("/topic/messages")
    public MessageDto sendMessage(@Payload MessageCreateRequest request) {
        Message message = messageService.create(request);
        return MessageDto.fromEntity(message);
    }

    @MessageMapping("/update/{id}")
    public void updateMessageViaSocket(
            @DestinationVariable String id,
            @Payload MessageUpdateRequest request
    ) {
        messageService.update(id, request);
    }

    @MessageMapping("/read/{id}")
    public void readMessageViaSocket(@DestinationVariable String id) {
        messageService.isReadMessage(id);
    }

    @MessageMapping("/delete/{id}")
    public void deleteMessageViaSocket(@DestinationVariable String id) {
        messageService.delete(id);
    }
}
