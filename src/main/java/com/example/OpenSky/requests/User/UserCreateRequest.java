package com.example.OpenSky.requests.User;

import com.example.OpenSky.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.catalina.Role;
import org.apache.catalina.connector.Request;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@SuperBuilder
public class UserCreateRequest extends RegisterRequest {
    private UserRole role;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "0\\d{9}", message = "Số điện thoại phải 10 số và bắt đầu bằng 0")
    private String phoneNumber;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate dob;

    @NotBlank(message = "Số CMND/Căn cước không được để trống")
    @Pattern(regexp = "\\d{12}", message = "CMND/Căn cước phải gồm 12 chữ số")
    private String citizenId;

    @NotNull
    private MultipartFile avatar;

}
