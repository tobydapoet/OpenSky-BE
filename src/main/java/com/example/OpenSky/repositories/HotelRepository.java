package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Hotel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,String> {
    @Query("SELECT h FROM Hotel h " +
            "WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword , '%'))")
    Page<Hotel> searchHotel(@Param("keyword") String keyword, Pageable pageable);

    Optional<Hotel> findByEmail(String email);
}
