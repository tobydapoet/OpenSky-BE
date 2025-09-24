package com.example.OpenSky.repositories;


import com.example.OpenSky.entities.Image;
import com.example.OpenSky.enums.TableTypeImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image,String> {
    List<Image> findByTypeIdAndTableType(String typeId, TableTypeImage tableType);
    void deleteByUrl(String url);
}
