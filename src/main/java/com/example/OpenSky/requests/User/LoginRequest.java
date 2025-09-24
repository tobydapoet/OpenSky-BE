package com.example.OpenSky.requests.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Sai định dạng email")
    private String email;

    @NotBlank(message = "Password không được để trống")
    private String password;
}