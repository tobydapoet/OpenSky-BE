package com.example.OpenSky.requests.ScheduleItinerary;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleItineraryUpdateRequest {
    private LocalDateTime endTime;
}
