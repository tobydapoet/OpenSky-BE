package com.example.OpenSky.dtos.Hotel;

import com.example.OpenSky.dtos.Image.ImageSummaryDto;
import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Hotel;
import com.example.OpenSky.entities.Image;
import com.example.OpenSky.enums.HotelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class HotelDto {
    private String id;

    private String email;

    private UserSummaryDto user;

    private String address;

    private String province;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String name;

    private String description;

    private HotelStatus status ;

    private Integer star;

    private LocalDateTime createdAt;

    private List<ImageSummaryDto> images;


    public static HotelDto fromEntity(Hotel hotel, List<Image> images) {
        return new HotelDto(
                hotel.getId(),
                hotel.getEmail(),
                UserSummaryDto.fromEntity(hotel.getUser()),
                hotel.getAddress(),
                hotel.getProvince(),
                hotel.getLongitude(),
                hotel.getLatitude(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getStatus(),
                hotel.getStar(),
                hotel.getCreatedAt(),
                images == null ? null : images.stream()
                        .map(ImageSummaryDto::fromEntity)
                        .toList()
        );
    }
}
