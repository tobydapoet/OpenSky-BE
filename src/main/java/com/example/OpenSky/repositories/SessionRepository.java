package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, String> {
    Session findByToken(String token);
}
