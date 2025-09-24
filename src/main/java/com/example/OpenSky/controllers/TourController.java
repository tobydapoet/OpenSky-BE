package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.requests.Tour.TourCreateRequest;
import com.example.OpenSky.requests.Tour.TourUpdateRequest;
import com.example.OpenSky.services.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @Operation(summary = "Lấy ra tất cả tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping
    public Page<TourDto> getTours(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return tourService.getTours(page, size);
    }

    @Operation(summary = "Lấy tour theo id",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @GetMapping("/{id}")
    public TourDto getById(@PathVariable String id) {
        return tourService.getTourWithImage(id);
    }

    @Operation(summary = "Tìm kiếm tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/search")
    public Page<TourDto> searchTour(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return tourService.searchTour(keyword, page, size);
    }

    @Operation(summary = "Tạo tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TourDto createTour(@ModelAttribute TourCreateRequest req, HttpServletRequest httpReq) {
        String authHeader = httpReq.getHeader("Authorization");
        String token =  authHeader.replace("Bearer ", "");
        return tourService.create(req,token);
    }

    @Operation(summary = "Cập nhật tour",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PutMapping(value = "/{id}",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public TourDto updateTour(@PathVariable String id, @ModelAttribute TourUpdateRequest req) {
        return tourService.update(id,req);
    }
}
