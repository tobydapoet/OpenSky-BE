package com.example.OpenSky.requests.Tour;

import com.example.OpenSky.entities.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class TourUpdateRequest {
    private String name;
    private Integer numberOfDays;
    private Integer maxPeople;
    private String description;
    private List<MultipartFile> files;
}
