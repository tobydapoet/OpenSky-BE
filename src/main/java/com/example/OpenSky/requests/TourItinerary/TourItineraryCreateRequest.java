package com.example.OpenSky.requests.TourItinerary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourItineraryCreateRequest {
    @NotBlank(message = "id tour không được để trống")
    private String tourId;

    @NotNull(message = "số ngày đi không được để trống")
    private Integer dayNumber;

    @NotBlank(message = "Vị trí không được để trống")
    private String location;

    private String description;

}
