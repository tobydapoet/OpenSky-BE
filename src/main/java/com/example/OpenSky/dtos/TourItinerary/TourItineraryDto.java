package com.example.OpenSky.dtos.TourItinerary;

import com.example.OpenSky.dtos.Tour.TourSummaryDto;
import com.example.OpenSky.entities.TourItinerary;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourItineraryDto {
    private String id;

    private TourSummaryDto tour;

    private Integer dayNumber;

    private String location;

    private String description;

    public static TourItineraryDto fromEntity(TourItinerary tourItinerary) {
        return new TourItineraryDto(
                tourItinerary.getId(),
                TourSummaryDto.fromEntity(tourItinerary.getTour()),
                tourItinerary.getDayNumber(),
                tourItinerary.getLocation(),
                tourItinerary.getDescription()
        );
    }
}
