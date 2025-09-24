package com.example.OpenSky.services;

import com.example.OpenSky.entities.Bill;
import com.example.OpenSky.entities.Refund;
import com.example.OpenSky.repositories.RefundRepository;
import com.example.OpenSky.requests.Refund.RefundRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RefundService {
    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    @Lazy
    private BillService billService;

    public Page<Refund> findAll( int page ,int size) {
       Pageable pageable = PageRequest.of(page, size);
       return refundRepository.findAll(pageable);
    }

    public Refund findById(String id) {
        return refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu hoàn tiền này!"));
    }

    public Refund findByBillId(String billId) {
        return refundRepository.findByBill_Id(billId)
                .orElseThrow(() -> new RuntimeException("Không tim thấy yêu cầu hoàn tiền này!"));
    }

    @Transactional
    public Refund create(RefundRequest req) {
        Refund refund = new Refund();
        billService.findById(req.getBillId());
        refund.setBill(refund.getBill());
        refund.setDescription(req.getDescription());
        Refund savedRefund = refundRepository.save(refund);
        billService.update(req.getBillId());
        return savedRefund;
    }
}
