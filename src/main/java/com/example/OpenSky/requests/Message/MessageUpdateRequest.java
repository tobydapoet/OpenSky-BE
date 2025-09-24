package com.example.OpenSky.requests.Message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageUpdateRequest {
    private String message;

}
