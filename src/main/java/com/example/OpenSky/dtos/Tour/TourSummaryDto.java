package com.example.OpenSky.dtos.Tour;

import com.example.OpenSky.entities.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourSummaryDto {
    private String id;
    private String name;
    private Integer star;
    private Integer numberOfDays;
    private Integer maxPeople;
    private String description;

    public static TourSummaryDto fromEntity(Tour tour) {
        return new TourSummaryDto(
                tour.getId(),
                tour.getName(),
                tour.getStar(),
                tour.getNumberOfDays(),
                tour.getMaxPeople(),
                tour.getDescription()
        );
    }
}
