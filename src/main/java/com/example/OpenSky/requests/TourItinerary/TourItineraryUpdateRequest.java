package com.example.OpenSky.requests.TourItinerary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourItineraryUpdateRequest {
    private Integer dayNumber;

    private String location;

    private String description;

}
