package com.example.OpenSky.requests.Message;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageCreateRequest {
    @NotBlank(message = "Người gửi không được bỏ trống")
    private String senderId;

    @NotBlank(message = "Người nhận không được bỏ trống")
    private String receiverId;

    private String message ;

}
