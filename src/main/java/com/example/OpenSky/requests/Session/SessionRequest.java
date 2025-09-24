package com.example.OpenSky.requests.Session;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionRequest {
    @NotBlank(message = "id người dùng không được để trống")
    private String userId;

    @NotBlank
    private String token;


}
