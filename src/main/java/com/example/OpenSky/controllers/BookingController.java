package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Booking.BookingDto;
import com.example.OpenSky.entities.Booking;
import com.example.OpenSky.requests.Booking.BookingCreateRequest;
import com.example.OpenSky.requests.Booking.BookingUpdateRequest;
import com.example.OpenSky.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Tạo booking",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public BookingDto create(HttpServletRequest httpReq, @RequestBody BookingCreateRequest req) {
        String authHeader = httpReq.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        Booking booking = bookingService.create(req,token);
        return bookingService.getBookingDto(booking);
    }

    @Operation(summary = "Lấy theo booking Id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping
    public BookingDto findById(String id) {
        Booking booking = bookingService.findById(id);
        return bookingService.getBookingDto(booking);
    }

    @Operation(summary = "Sửa booking",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PutMapping("/{id}")
    public BookingDto update(@PathVariable String id,@RequestBody BookingUpdateRequest req) {
        Booking booking = bookingService.update(id, req);
        return bookingService.getBookingDto(booking);
    }
}
