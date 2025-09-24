package com.example.OpenSky.dtos.HotelRoom;

import com.example.OpenSky.dtos.Image.ImageSummaryDto;
import com.example.OpenSky.entities.HotelRoom;
import com.example.OpenSky.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelRoomDto {
    private String id;
    private String hotelId;
    private String roomName;
    private String roomType;
    private String address;
    private Integer price;
    private Integer maxPeople;
    private List<ImageSummaryDto> images;

    public static HotelRoomDto fromEntity(HotelRoom hotelRoom, List<Image> images) {
        return new HotelRoomDto(
                hotelRoom.getId(),
                hotelRoom.getHotel().getId(),
                hotelRoom.getRoomName(),
                hotelRoom.getRoomType(),
                hotelRoom.getAddress(),
                hotelRoom.getPrice(),
                hotelRoom.getMaxPeople(),
                images == null ? null : images.stream()
                        .map(ImageSummaryDto::fromEntity)
                        .toList()
        );
    }
}
