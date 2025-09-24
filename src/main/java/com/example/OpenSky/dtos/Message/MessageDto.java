package com.example.OpenSky.dtos.Message;

import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MessageDto {
    private String id;
    private UserSummaryDto sender;
    private UserSummaryDto receiver;
    private String message;
    private boolean isReaded;
    private LocalDateTime createdAt;

    public static MessageDto fromEntity (Message message) {
        return new MessageDto(
                message.getId(),
                UserSummaryDto.fromEntity(message.getSenderUser()),
                UserSummaryDto.fromEntity(message.getReceiverUser()),
                message.getMessage(),
                message.isReaded(),
                message.getCreatedAt()
        );
    }
}
