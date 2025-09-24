package com.example.OpenSky.requests.Booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingUpdateRequest {
    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;
}
