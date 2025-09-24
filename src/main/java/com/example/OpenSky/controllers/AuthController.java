package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Session.SessionDto;
import com.example.OpenSky.dtos.User.RegisterDto;
import com.example.OpenSky.entities.Session;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.requests.User.LoginRequest;
import com.example.OpenSky.requests.User.RegisterRequest;
import com.example.OpenSky.services.SessionService;
import com.example.OpenSky.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Operation(summary = "refresh token")
    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> body) {
        String token = body.get("refreshToken");
        return sessionService.refresh(token);
    }

    @Operation(summary = "Đăng nhập bằng google")
    @GetMapping("google/login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @Operation(summary = "Đăng ký")
    @PostMapping("/register")
    public RegisterDto register(@RequestBody RegisterRequest registerRequest) {
        User savedUser = userService.register(registerRequest);
        return new RegisterDto(savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFullName(),
                savedUser.getCreatedAt());
    }

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public Map<String,String> login(@RequestBody LoginRequest req) {
        return sessionService.login(req);
    }

    @Operation(summary = "Đăng xuất")
    @PostMapping("/logout")
    public Map<String,String> logout(@RequestBody Map<String, String> body) {
        String token = body.get("refreshToken");
        return sessionService.logout(token);
    }
}
