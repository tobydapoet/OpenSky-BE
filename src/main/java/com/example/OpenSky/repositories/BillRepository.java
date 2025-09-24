package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,String> {
    Page<Bill> findByBooking_User_Id( String customerId, Pageable pageable);
}
