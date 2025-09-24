package com.example.OpenSky.dtos.User;

import com.example.OpenSky.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDto {
    private String id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private String avatarURL;

    public static UserSummaryDto fromEntity(User user) {
        return new UserSummaryDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getAvatarURL());
    }
}
