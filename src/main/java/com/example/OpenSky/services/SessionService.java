package com.example.OpenSky.services;

import com.example.OpenSky.entities.Session;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.repositories.SessionRepository;
import com.example.OpenSky.requests.Session.SessionRequest;
import com.example.OpenSky.requests.User.LoginRequest;
import com.example.OpenSky.requests.User.UserGoogleCreateRequest;
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

    public void logout(String id){
        Session session = findById(id);
        sessionRepository.delete(session);
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
                "access_token", access_token,
                "refresh_token", refresh_token
        );
    }

    public Map<String,String> googleLogin(UserGoogleCreateRequest req) {
        User user = userService.findByEmail(req.getEmail());
        if(user == null){
            user = userService.createWithGoogle(req);
        }
        Session session = new Session();
        session.setUser(user);
        session = sessionRepository.saveAndFlush(session);
        String access_token = jwtService.generateAccessToken(session);
        String refresh_token = jwtService.generateRefreshToken(session);
        session.setToken(refresh_token);
        sessionRepository.save(session);
        return Map.of(
                "access_token",access_token,
                "refresh_token",refresh_token
        );
    }

    public String refresh(String refresh_token, String id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session with id " + id + " not found"));
        if(!session.getToken().equals(refresh_token)){
            throw new RuntimeException("Invalid refresh token");
        }
        return jwtService.generateAccessToken(session);
    }

}
