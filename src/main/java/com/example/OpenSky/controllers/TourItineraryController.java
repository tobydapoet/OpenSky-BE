package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.TourItinerary.TourItineraryDto;
import com.example.OpenSky.entities.TourItinerary;
import com.example.OpenSky.requests.TourItinerary.TourItineraryCreateRequest;
import com.example.OpenSky.requests.TourItinerary.TourItineraryUpdateRequest;
import com.example.OpenSky.services.TourItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour_itineraríes")
public class TourItineraryController {
    @Autowired
    private TourItineraryService tourItineraryService;


    @Operation(summary = "Xem hành trình tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/tour/{tourId}")
    public List<TourItineraryDto> findByTour(@PathVariable String tourId) {
        List<TourItinerary> tourItineraries = tourItineraryService.findByTourId(tourId);
        return tourItineraries.stream()
                .map(TourItineraryDto::fromEntity)
                .toList();
    }


    @Operation(summary = "ấy hành trình theo id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public TourItineraryDto findById(@PathVariable String id) {
        TourItinerary tourItinerary = tourItineraryService.findById(id);
        return TourItineraryDto.fromEntity(tourItinerary);
    }


    @Operation(summary = "Tạo hành trình cho tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PostMapping
    public TourItineraryDto create(@RequestBody TourItineraryCreateRequest req) {
        TourItinerary tourItinerary = tourItineraryService.create(req);
        return  TourItineraryDto.fromEntity(tourItinerary);
    }

    @Operation(summary = "cập nhật hành trình cho tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PutMapping("/{id}")
    public TourItineraryDto update(@PathVariable String id, @RequestBody TourItineraryUpdateRequest req) {
        TourItinerary tourItinerary = tourItineraryService.update(id, req);
        return TourItineraryDto.fromEntity(tourItinerary);
    }
}
