package com.example.OpenSky.requests.HotelRoom;

import com.example.OpenSky.entities.Hotel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class HotelRoomCreateRequest {
    @NotBlank(message = "Hotel id không được bỏ trống")
    private String hotelId;

    @NotBlank(message = "Tên phòng không được bỏ trống")
    private String roomName;

    @NotBlank(message = "Loại phòng không ược bỏ trống")
    private String roomType;

    private String address;

    @NotNull(message = "Giá phòng không được bỏ trống")
    private Integer price;

    @NotNull(message = "Số lượng người trong phòng không được bỏ trống")
    private Integer maxPeople;
    List<MultipartFile> files;

}
