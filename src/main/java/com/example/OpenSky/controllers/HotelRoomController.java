package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.HotelRoom.HotelRoomDto;
import com.example.OpenSky.requests.HotelRoom.HotelRoomCreateRequest;
import com.example.OpenSky.requests.HotelRoom.HotelRoomUpdateRequest;
import com.example.OpenSky.services.HotelRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class HotelRoomController {
    @Autowired
    private HotelRoomService hotelRoomService;

    @Operation(summary = "Tạo hotel room",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('HOTEL')")
    @GetMapping("/hotel/{id}")
    public Page<HotelRoomDto> getHotels(
            @RequestParam String hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return hotelRoomService.findByHotel(hotelId, page, size);
    }

    @Operation(summary = "Lấy hotel room theo id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public HotelRoomDto findById(@PathVariable String id) {
        return hotelRoomService.findByIdWithImage(id);
    }

    @Operation(summary = "Tìm kiếm hotel room",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/search")
    public Page<HotelRoomDto> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam String hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return hotelRoomService.searchRooms(keyword, hotelId,page, size);
    }

    @Operation(summary = "Tạo hotel room",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('HOTEL')")
    @PostMapping
    public HotelRoomDto createHotel(@ModelAttribute HotelRoomCreateRequest req) {
        return hotelRoomService.create(req);
    }

    @Operation(summary = "Cập nhật hotel room",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('HOTEL')")
    @PutMapping("/{id}")
    public HotelRoomDto updateHotel(@PathVariable String id,@ModelAttribute HotelRoomUpdateRequest req) {
        return hotelRoomService.update(id,req);
    }
}
