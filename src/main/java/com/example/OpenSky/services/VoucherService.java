package com.example.OpenSky.services;

import com.example.OpenSky.entities.Voucher;
import com.example.OpenSky.repositories.VoucherRepository;
import com.example.OpenSky.requests.Voucher.VoucherCreateRequest;
import com.example.OpenSky.requests.Voucher.VoucherUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public Page<Voucher> getVouchers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  voucherRepository.findAll(pageable);
    }

    public Voucher findById(String id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this voucher!"));
    }

    public Page<Voucher> searchVoucherByDate(String date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        LocalDate localDate = LocalDate.parse(date);

        return voucherRepository.findAllByDate(localDate, pageable);
    }

    public Voucher create(VoucherCreateRequest req) {
        Voucher voucher = new Voucher();
        voucher.setDescription(req.getDescription());
        voucher.setTableType(req.getTableType());
        voucher.setPercent(req.getPercent());
        voucher.setDescription(req.getDescription());
        voucher.setStartDate(req.getStartDate());
        voucher.setEndDate(req.getEndDate());
        return voucherRepository.save(voucher);
    }

    public Voucher update(String id, VoucherUpdateRequest req) {
        Voucher voucher = findById(id);
        if(req.getDescription() != null) {
            voucher.setDescription(req.getDescription());
        }
        if(req.getStartDate() != null) {
            if (voucher.getEndDate() != null && !req.getStartDate().isBefore(voucher.getEndDate())) {
                throw new RuntimeException("StartDate must be before EndDate");
            }
            voucher.setStartDate(req.getStartDate());
        }
        if(req.getEndDate() != null) {
            if (voucher.getStartDate() != null && !voucher.getStartDate().isBefore(req.getEndDate())) {
                throw new RuntimeException("StartDate must be before EndDate");
            }
            voucher.setEndDate(req.getEndDate());
        }
        if(req.getTableType() != null) {
            voucher.setTableType(req.getTableType());
        }
        return voucherRepository.save(voucher);
    }
}
