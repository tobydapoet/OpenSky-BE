package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.UserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByCitizenId(String citizenId);
    Page<User> findByRoleIn(List<UserRole> roles, Pageable pageable);

    @Query("""
    SELECT u FROM User u
    WHERE (LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND u.role IN :roles
""")
    Page<User> searchUsers(@Param("keyword") String keyword,
                           @Param("roles") List<UserRole> roles,
                           Pageable pageable);
}
