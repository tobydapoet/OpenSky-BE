package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Feedback.FeedbackDto;
import com.example.OpenSky.dtos.Feedback.FeedbackSummaryDto;
import com.example.OpenSky.entities.Feedback;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.requests.FeedBack.FeedBackRequest;
import com.example.OpenSky.services.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @Operation(summary = "Tạo feedback",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("tour/{tourId}")
    public List<FeedbackSummaryDto> findByTour(
            @RequestParam String tourId
    ) {
        return feedbackService.findByTour(tourId);
    }

    @Operation(summary = "xóa yêu cầu hoàn tiền theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasAnyRole('HOTEL','ADMIN','SUPERVISOR')")
    @GetMapping("hotel/{hotelId}")
    public List<FeedbackSummaryDto> findByHotel(
            @RequestParam String hotelId
    ) {
        return feedbackService.findByHotel(hotelId);
    }

    @Operation(summary = "xóa yêu cầu hoàn tiền theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/{id}")
    public FeedbackDto findById(@PathVariable String id) {
        return feedbackService.findByIdWithTable(id);
    }

    @Operation(summary = "tạo yêu cầu hoàn tiền theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping
    public FeedbackDto create(@RequestBody FeedBackRequest req, HttpServletRequest httpReq) {
        String authHeader  = httpReq.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        return feedbackService.create(req,token);
    }

    @Operation(summary = "xóa yêu cầu hoàn tiền theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        feedbackService.deleteById(id);
    }
}
