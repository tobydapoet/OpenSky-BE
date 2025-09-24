package com.example.OpenSky.services;

import com.example.OpenSky.entities.Session;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.repositories.SessionRepository;
import com.example.OpenSky.requests.Session.SessionRequest;
import com.example.OpenSky.requests.User.LoginRequest;
import com.example.OpenSky.requests.User.UserGoogleCreateRequest;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    public Session findById(String id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session with id " + id + " not found"));
    }

    public Map<String,String> logout(String token) {
        Claims claims = jwtService.parseRefreshToken(token);
        String sessionId = claims.getSubject();
        sessionRepository.deleteById(sessionId);
        return Map.of("message", "Đăng xuất thành công!");
    }

    public Map<String, String> login(LoginRequest req) {
        User existingUser = userService.login(req);

        Session session = new Session();
        session.setUser(existingUser);
        session = sessionRepository.saveAndFlush(session);
        String access_token = jwtService.generateAccessToken(session);
        String refresh_token = jwtService.generateRefreshToken(session);

        session.setToken(refresh_token);
        sessionRepository.save(session);

        return Map.of(
                "accessToken", access_token,
                "refreshToken", refresh_token
        );
    }

    public Map<String,String> googleLogin(UserGoogleCreateRequest req) {
        User user = userService.findByEmail(req.getEmail())
                .orElseGet(() -> userService.createWithGoogle(req));
        Session session = new Session();
        session.setUser(user);
        session = sessionRepository.saveAndFlush(session);
        String access_token = jwtService.generateAccessToken(session);
        String refresh_token = jwtService.generateRefreshToken(session);
        session.setToken(refresh_token);
        sessionRepository.save(session);
        return Map.of(
                "accessToken",access_token,
                "refreshToken",refresh_token
        );
    }

    public Map<String, String> refresh(String refresh_token) {
        Session session = sessionRepository.findByToken(refresh_token);
        if(session == null){
            throw new RuntimeException("Invalid refresh token");
        }
        return Map.of("accessToken", jwtService.generateAccessToken(session));
    }

}
