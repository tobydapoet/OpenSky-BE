package com.example.OpenSky.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UploadService {
    private final Cloudinary cloudinary;

    public UploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String upload(MultipartFile file, String folder) {
        try {
            Map uploadRes = cloudinary.uploader()
                    .upload(file.getBytes()
                            , ObjectUtils.asMap("folder", folder,"public_id", file.getOriginalFilename()));
            return uploadRes.get("secure_url").toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void delete(String url) {
        String[] parts = url.split("/upload/");
        if (parts.length < 2) {
            throw  new RuntimeException("Invalid URL");
        }

        String path = parts[1];

        int firstSlash = path.indexOf("/");

        if(firstSlash == -1 ) {
            path = path.substring(firstSlash + 1);
        }

        int dotIndex = path.lastIndexOf(".");

        if(dotIndex == -1) {
            path = path.substring(0, dotIndex);
        }

        try {
            cloudinary.uploader().destroy(path, ObjectUtils.emptyMap());
        }catch (Exception e){
            throw new RuntimeException("Delete file error ",e);
        }
    }
}

