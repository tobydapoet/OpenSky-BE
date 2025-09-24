package com.example.OpenSky.requests.Schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleCreateRequest {
    @NotBlank(message = "id người dùng không được bỏ trống")
    private String userId;

    @NotBlank(message = "id tour không được bỏ trống")
    private String tourId;

    @NotBlank(message = "Thời gian khởi hành không được bỏ trống")
    private LocalDateTime startTime;
}
