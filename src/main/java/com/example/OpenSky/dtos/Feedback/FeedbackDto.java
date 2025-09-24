package com.example.OpenSky.dtos.Feedback;

import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Feedback;
import com.example.OpenSky.entities.Hotel;
import com.example.OpenSky.entities.Tour;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.services.HotelService;
import com.example.OpenSky.services.TourService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackDto {
    private String id;
    private UserSummaryDto user;
    private TableType tableType;
    private Object table;
    private Integer rate;
    private String description;

    public static FeedbackDto fromEntity(
            Feedback feedback,
            HotelService hotelService,
            TourService tourService
    ) {

        if (feedback == null) return null;

        Object table = null;

        if (feedback.getTableType() == TableType.Hotel) {
            Hotel hotel = hotelService.findById(feedback.getTableId());
            table = HotelDto.fromEntity(hotel, null);
        } else if (feedback.getTableType() == TableType.Tour) {
            Tour tour = tourService.findById(feedback.getTableId());
            table = TourDto.fromEntity(tour, null);
        }

        return new FeedbackDto(
                feedback.getId(),
                UserSummaryDto.fromEntity(feedback.getUser()),
                feedback.getTableType(),
                table,
                feedback.getRate(),
                feedback.getDescription()
        );
    }
}
