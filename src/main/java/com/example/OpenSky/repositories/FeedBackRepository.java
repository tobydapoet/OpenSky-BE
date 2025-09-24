package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Feedback;
import com.example.OpenSky.entities.Image;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.enums.TableTypeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findByTableIdAndTableType(String tableId, TableType tableType);

}
