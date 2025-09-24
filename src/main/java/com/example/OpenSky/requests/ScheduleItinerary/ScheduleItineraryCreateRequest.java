package com.example.OpenSky.requests.ScheduleItinerary;

import com.example.OpenSky.entities.Schedule;
import com.example.OpenSky.entities.TourItinerary;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleItineraryCreateRequest {
    @NotBlank(message = "id hành trình không được bỏ trống")
    private String scheduleId;

    @NotBlank(message = "id hành trình tour không được bỏ trống")
    private String tourItineraryId;
    private LocalDateTime endTime;
}
