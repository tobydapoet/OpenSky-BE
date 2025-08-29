package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByPhone(String phone);
    User findByCitizenId(String citizenId);

    @Query("""
    SELECT u FROM User u 
    WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) 
       OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) 
       OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(u.phone) LIKE LOWER(CONCAT('%' , :keyword, '%'))
    """)
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
}
