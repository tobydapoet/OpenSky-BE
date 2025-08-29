package com.example.OpenSky.controllers;

import com.example.OpenSky.entities.Session;
import com.example.OpenSky.requests.User.LoginRequest;
import com.example.OpenSky.services.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @GetMapping("/{id}")
    public Session get(@PathVariable String id) {
        return  sessionService.findById(id);
    }

    @PostMapping("/refresh/{id}")
    public String refresh(@RequestHeader("Authorization") String header, @PathVariable String id) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String refresh_token = header.substring(7);
        return sessionService.refresh(refresh_token,id);
    }

    @GetMapping("google/login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody LoginRequest req) {
        return sessionService.login(req);
    }

    @DeleteMapping("/{id}")
    public void logout(@PathVariable String id) {
        sessionService.logout(id);
    }

}
