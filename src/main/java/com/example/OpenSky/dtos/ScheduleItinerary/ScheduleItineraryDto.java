package com.example.OpenSky.dtos.ScheduleItinerary;

import com.example.OpenSky.dtos.Schedule.ScheduleDto;
import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.dtos.TourItinerary.TourItineraryDto;
import com.example.OpenSky.entities.ScheduleItinerary;
import com.example.OpenSky.entities.TourItinerary;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleItineraryDto {
    private String id;
    private ScheduleDto schedule;
    private TourItineraryDto tourItinerary;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static ScheduleItineraryDto fromEntity(ScheduleItinerary scheduleItinerary) {
        return new ScheduleItineraryDto(
                scheduleItinerary.getId(),
                ScheduleDto.fromEntity(scheduleItinerary.getSchedule()),
                TourItineraryDto.fromEntity(scheduleItinerary.getTourItinerary()),
                scheduleItinerary.getStartTime(),
                scheduleItinerary.getEndTime()
        );
    }
}
