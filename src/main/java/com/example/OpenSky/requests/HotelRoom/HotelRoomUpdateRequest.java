package com.example.OpenSky.requests.HotelRoom;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class HotelRoomUpdateRequest {
    private String roomName;
    private String roomType;
    private String address;
    private Integer price;
    private Integer maxPeople;
    List<MultipartFile> files;
}
