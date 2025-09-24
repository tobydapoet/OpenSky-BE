package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Schedule.ScheduleDto;
import com.example.OpenSky.entities.Schedule;
import com.example.OpenSky.requests.Schedule.ScheduleCreateRequest;
import com.example.OpenSky.requests.Schedule.ScheduleUpdateRequest;
import com.example.OpenSky.services.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;


    @Operation(summary = "Xem các lịch trình của người dùng",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/user")
    public Page<ScheduleDto> findByUserId(
            @RequestParam String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Schedule> schedules = scheduleService.findByUser_Id(id, page, size);
        return schedules.map(ScheduleDto::fromEntity);
    }

    @Operation(summary = "Xem lịch trình dựa trên tourId",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/tour")
    public Page<ScheduleDto> findByTourId(
            @RequestParam String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Schedule> schedules = scheduleService.findByTour_Id(id, page, size);
        return schedules.map(ScheduleDto::fromEntity);
    }

    @Operation(summary = "Xem lịch trình dựa trên Id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public ScheduleDto findById(String id) {
        Schedule schedule = scheduleService.findById(id);
        return ScheduleDto.fromEntity(schedule);
    }

    @Operation(summary = "Tạo lịch trình",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PostMapping
    public ScheduleDto create(@RequestBody ScheduleCreateRequest req) {
        Schedule schedule = scheduleService.create(req);
        return ScheduleDto.fromEntity(schedule);
    }

    @Operation(summary = "Cập nhật lịch trình",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PutMapping("/{id}")
    public ScheduleDto update(@PathVariable String id, @RequestBody ScheduleUpdateRequest req) {
        Schedule schedule = scheduleService.update(id, req);
        return ScheduleDto.fromEntity(schedule);
    }
}
