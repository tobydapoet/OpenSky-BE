package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillDetailRepository extends JpaRepository<BillDetail,String> {
    List<BillDetail> findByBill_Id(String billDetailId);

}
