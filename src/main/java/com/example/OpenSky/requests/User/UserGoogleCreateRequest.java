package com.example.OpenSky.requests.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGoogleCreateRequest {
    @Email
    private String email;

    @NotBlank
    private String fullName;

    @NotBlank
    private String providerId;

    @NotBlank
    private String avatarURL;

}
