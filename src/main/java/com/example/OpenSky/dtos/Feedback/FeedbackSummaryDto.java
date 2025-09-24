package com.example.OpenSky.dtos.Feedback;

import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Feedback;
import com.example.OpenSky.enums.TableType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackSummaryDto {
    private String id;
    private UserSummaryDto user;
    private Integer rate;
    private String description;

    public static FeedbackSummaryDto fromEntity(Feedback feedback) {
        return new FeedbackSummaryDto(
                feedback.getId(),
                UserSummaryDto.fromEntity(feedback.getUser()),
                feedback.getRate(),
                feedback.getDescription()
        );
    }
}
