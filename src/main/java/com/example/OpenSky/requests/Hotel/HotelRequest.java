package com.example.OpenSky.requests.Hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class HotelRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Sai định dạng email")
    private String email;

    @NotBlank(message = "User id không được để trống")
    private String userId;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Tỉnh thành không được để trống")
    private String province;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @NotBlank(message = "Tên khách sạn không được bỏ trống")
    private String name;

    private String description;

    private List<MultipartFile> files;

}
