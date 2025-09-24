package com.example.OpenSky.requests.Tour;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class TourCreateRequest {
    @NotBlank
    private String name;

    @NotNull(message = "Số ngày đi không được bỏ trống")
    private Integer numberOfDays;

    @NotNull(message = "Số lượng người tham gia không được để trống")
    private Integer maxPeople;

    private String description;

    private List<MultipartFile> files;
}
