package com.example.OpenSky.handler;

import com.example.OpenSky.requests.User.UserGoogleCreateRequest;
import com.example.OpenSky.services.SessionService;
import com.example.OpenSky.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OauthHandler implements AuthenticationSuccessHandler {
    @Autowired
    @Lazy
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest req,
            HttpServletResponse res,
            Authentication authentication
    )throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        UserGoogleCreateRequest dto = UserGoogleCreateRequest.builder()
                .email(oAuth2User.getAttribute("email"))
                .avatarURL(oAuth2User.getAttribute("picture"))
                .providerId(oAuth2User.getAttribute("sub"))
                .fullName(oAuth2User.getAttribute("name"))
                .build();

        Map<String, String> token = sessionService.googleLogin(dto);

        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write(objectMapper.writeValueAsString(token));
        res.getWriter().flush();
    }

}
