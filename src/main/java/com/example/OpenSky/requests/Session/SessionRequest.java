package com.example.OpenSky.requests.Session;

import jakarta.validation.constraints.NotBlank;


public class SessionRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
