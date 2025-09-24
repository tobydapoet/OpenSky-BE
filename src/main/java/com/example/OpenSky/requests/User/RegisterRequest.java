package com.example.OpenSky.requests.User;

import com.example.OpenSky.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class RegisterRequest {
    @NotBlank(message = "Họ tên không được để trống")
    String fullName;

    @Email(message = "Sai định dạng email")
    @NotBlank(message = "Email không được để trống")
    String email;

    @NotBlank(message = "Password không được để trống")
    String password;

}
