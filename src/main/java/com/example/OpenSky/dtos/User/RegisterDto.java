package com.example.OpenSky.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String id;

    private String email;

    private String fullName;

    private LocalDateTime createdAt;
}
