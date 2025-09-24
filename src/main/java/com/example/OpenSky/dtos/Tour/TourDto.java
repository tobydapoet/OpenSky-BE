package com.example.OpenSky.dtos.Tour;
import com.example.OpenSky.dtos.Image.ImageSummaryDto;
import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Image;
import com.example.OpenSky.entities.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TourDto {
    private String id;
    private UserSummaryDto user;
    private String name;
    private Integer star;
    private Integer numberOfDays;
    private Integer maxPeople;
    private String description;
    private LocalDateTime createdAt;
    private List<ImageSummaryDto> images;

    public static TourDto fromEntity(Tour tour, List<Image> images)  {
        return new TourDto(
                tour.getId(),
                UserSummaryDto.fromEntity(tour.getUser()),
                tour.getName(),
                tour.getStar(),
                tour.getNumberOfDays(),
                tour.getMaxPeople(),
                tour.getDescription(),
                tour.getCreatedAt(),
                images == null ? null :images.stream()
                        .map(ImageSummaryDto::fromEntity)
                        .toList()
        );
    }

}
