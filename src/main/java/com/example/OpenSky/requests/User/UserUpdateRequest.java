package com.example.OpenSky.requests.User;

import com.example.OpenSky.enums.UserRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserUpdateRequest {
    private String fullName;
    private String phoneNumber;
    private String citizenId;
    private LocalDate dob;
    private UserRole role;
    private MultipartFile avatar;
}
