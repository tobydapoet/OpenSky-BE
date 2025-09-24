package com.example.OpenSky.dtos.Schedule;

import com.example.OpenSky.dtos.Tour.TourSummaryDto;
import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Schedule;
import com.example.OpenSky.services.ScheduleService;
import com.example.OpenSky.services.UserService;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class ScheduleDto {
    private String id;
    private UserSummaryDto user;
    private TourSummaryDto tour;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberPeople;
    private LocalDateTime createdAt;

    public static ScheduleDto fromEntity(Schedule schedule) {
        return new ScheduleDto(
                schedule.getId(),
                UserSummaryDto.fromEntity(schedule.getUser()),
                TourSummaryDto.fromEntity(schedule.getTour()),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getNumberPeople(),
                schedule.getCreatedAt()
        );
    }
}
