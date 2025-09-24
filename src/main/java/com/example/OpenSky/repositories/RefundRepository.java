package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, String> {
    Optional<Refund> findByBill_Id(String id );
}
