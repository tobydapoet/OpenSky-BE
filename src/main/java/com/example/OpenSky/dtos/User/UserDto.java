package com.example.OpenSky.dtos.User;


import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserDto extends RegisterDto{
    private String phoneNumber;

    private String citizenId;

    private UserRole role;

    private LocalDate dob;

    private String avatarURL;

    public static UserDto fromEntity(User user){
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCreatedAt(),
                user.getPhoneNumber(),
                user.getCitizenId(),
                user.getRole(),
                user.getDob(),
                user.getAvatarURL()
        );
    }

    public UserDto(String id, String email, String fullName, LocalDateTime createdAt, String phoneNumber, String citizenId, UserRole role, LocalDate dob, String avatarURL) {
        super(id, email, fullName, createdAt);
        this.phoneNumber = phoneNumber;
        this.citizenId = citizenId;
        this.role = role;
        this.dob = dob;
        this.avatarURL = avatarURL;
    }
}
