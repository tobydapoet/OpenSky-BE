package com.example.OpenSky.dtos.Booking;

import com.example.OpenSky.dtos.Bill.BillDto;
import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Booking;
import com.example.OpenSky.entities.Hotel;
import com.example.OpenSky.enums.TableType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private String id;
    private UserSummaryDto user;
    private BillDto bill;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String notes;
    private LocalDateTime createdAt;
    private Object target;

    public static BookingDto fromEntity(Booking booking) {
        return new BookingDto(
                booking.getId(),
                UserSummaryDto.fromEntity(booking.getUser()),
                BillDto.fromEntity(booking.getBill()),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getNotes(),
                booking.getCreatedAt(),
                null
        );
    }
}
