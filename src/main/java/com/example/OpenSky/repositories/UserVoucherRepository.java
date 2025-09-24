package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.UserVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, String> {
    Page<UserVoucher> findByUser_Id(String userId, Pageable pageable);
    Page<UserVoucher> findByVoucher_Id(String voucherId, Pageable pageable);
    void deleteByUser_IdAndVoucher_Id(String userId, String voucherId);
}
