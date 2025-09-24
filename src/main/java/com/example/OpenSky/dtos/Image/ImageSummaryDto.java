package com.example.OpenSky.dtos.Image;

import com.example.OpenSky.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageSummaryDto {
    private String id;

    private String url;

    public static ImageSummaryDto fromEntity(Image image) {
        return  new ImageSummaryDto(image.getId(), image.getUrl());
    }
}
