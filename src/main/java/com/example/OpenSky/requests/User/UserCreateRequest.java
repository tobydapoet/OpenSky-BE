package com.example.OpenSky.requests.User;

import com.example.OpenSky.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import org.apache.catalina.Role;
import org.apache.catalina.connector.Request;

public class UserCreateRequest extends RegisterRequest {
    @NotBlank
    UserRole role;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
