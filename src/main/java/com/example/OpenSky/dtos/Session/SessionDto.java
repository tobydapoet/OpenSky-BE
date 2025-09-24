package com.example.OpenSky.dtos.Session;

import com.example.OpenSky.dtos.User.UserDto;
import com.example.OpenSky.entities.Session;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionDto {
    private String id;

    private UserDto user;

    private String token;

    private LocalDateTime createdAt;

    public  static SessionDto fromEntity(Session session){
        return new SessionDto(
                session.getId(),
                UserDto.fromEntity(session.getUser()),
                session.getToken(),
                session.getCreatedAt()
        );
    }
}
