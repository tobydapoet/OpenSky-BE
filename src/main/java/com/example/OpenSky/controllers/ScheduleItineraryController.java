package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Schedule.ScheduleDto;
import com.example.OpenSky.dtos.ScheduleItinerary.ScheduleItineraryDto;
import com.example.OpenSky.entities.ScheduleItinerary;
import com.example.OpenSky.requests.ScheduleItinerary.ScheduleItineraryCreateRequest;
import com.example.OpenSky.requests.ScheduleItinerary.ScheduleItineraryUpdateRequest;
import com.example.OpenSky.services.ScheduleItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule_itineraries")
public class ScheduleItineraryController {
    @Autowired
    private ScheduleItineraryService scheduleItineraryService;

    @Operation(summary = "Xem hành trình của lịch trình",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/schedule/{id}")
    public List<ScheduleItineraryDto> findByScheduleId(@PathVariable String id) {
        List<ScheduleItinerary> scheduleItineraries = scheduleItineraryService.findByScheduleId(id);
        return scheduleItineraries
                .stream()
                .map(ScheduleItineraryDto::fromEntity)
                .toList();
    }

    @GetMapping("/tour_itinerary/{id}")
    public List<ScheduleItineraryDto> findByTourItineraryId(@PathVariable String id) {
        List<ScheduleItinerary> scheduleItineraries = scheduleItineraryService.findByTourItineraryId(id);
        return scheduleItineraries
                .stream()
                .map(ScheduleItineraryDto::fromEntity)
                .toList();
    }

    @Operation(summary = "Lấy hành trình của lịch trình theo id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public ScheduleItineraryDto findById(@PathVariable String id) {
        ScheduleItinerary scheduleItinerary= scheduleItineraryService.findById(id);
        return ScheduleItineraryDto.fromEntity(scheduleItinerary);
    }

    @Operation(summary = "Tạo hành trình",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('TOURGUIDE')")
    @PostMapping
    public ScheduleItineraryDto create(@RequestBody ScheduleItineraryCreateRequest req) {
        ScheduleItinerary scheduleItinerary = scheduleItineraryService.create(req);
        return ScheduleItineraryDto.fromEntity(scheduleItinerary);
    }

    @Operation(summary = "Cập nhật hành trình",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('TOURGUIDE')")
    @PutMapping("/{id}")
    public ScheduleItineraryDto update(@PathVariable String id,@RequestBody ScheduleItineraryUpdateRequest req) {
        ScheduleItinerary scheduleItinerary = scheduleItineraryService.update(id,req);
        return ScheduleItineraryDto.fromEntity(scheduleItinerary);
    }

    @Operation(summary = "Xóa hành trình",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('TOURGUIDE')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        scheduleItineraryService.deleteById(id);
    }
}
