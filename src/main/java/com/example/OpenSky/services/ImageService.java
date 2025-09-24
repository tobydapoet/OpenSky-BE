package com.example.OpenSky.services;

import com.example.OpenSky.entities.Image;
import com.example.OpenSky.enums.TableTypeImage;
import com.example.OpenSky.repositories.ImageRepository;
import com.example.OpenSky.requests.Image.ImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UploadService uploadService;

    @Autowired
    @Lazy
    HotelService hotelService;

    public List<Image> findByTypeAndId(String tableId, TableTypeImage type) {
        return imageRepository.findByTypeIdAndTableType(tableId,type);
    }

    public Image findById(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image Not Found!"));
    }

    public Image create(ImageRequest req){
        Image image = new Image();
        image.setTypeId(req.getTypeId());
        image.setTableType(req.getTableType());
        String uploadImage = uploadService.upload(req.getFile(), req.getTableType().name().toLowerCase());
        image.setUrl(uploadImage);
        return imageRepository.save(image);
    }

    public void deleteByTypeIdAndTableType(String typeId, TableTypeImage tableType) {
        List<Image> images = imageRepository.findByTypeIdAndTableType(typeId, tableType);
        for (Image image : images) {
            uploadService.delete(image.getUrl());
            imageRepository.delete(image);
        }
    }

    public void delete(String id){
        Image image = findById(id);
        uploadService.delete(image.getUrl());
        imageRepository.deleteById(id);
    }

    public void deleteByUrl(String url){
        imageRepository.deleteByUrl(url);
    }
}
