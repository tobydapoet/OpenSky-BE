package com.example.OpenSky.requests.Image;

import com.example.OpenSky.enums.TableTypeImage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ImageRequest {
    @NotBlank
    private TableTypeImage tableType;

    @NotBlank
    private String typeId;

    @NotBlank
    MultipartFile file;

}
