package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Voucher;
import com.example.OpenSky.enums.BillStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface VoucherRepository extends JpaRepository<Voucher, String> {
    @Query("SELECT v FROM Voucher v " +
            "WHERE :date BETWEEN v.startDate AND v.endDate")
    Page<Voucher> findAllByDate(@Param("date") LocalDate date, Pageable pageable);

}
