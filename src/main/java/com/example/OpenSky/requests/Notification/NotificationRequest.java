package com.example.OpenSky.requests.Notification;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {
    @NotBlank(message = "id hóa đơn không được bỏ trống")
    private String billId;
}
