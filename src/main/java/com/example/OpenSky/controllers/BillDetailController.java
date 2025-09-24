package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.BillDetail.BillDetailDto;
import com.example.OpenSky.entities.BillDetail;
import com.example.OpenSky.requests.BillDetail.BillDetailCreateRequest;
import com.example.OpenSky.services.BillDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill_details")
public class BillDetailController {

    @Autowired
    BillDetailService billDetailService;

    @Operation(summary = "Lấy các bill detail theo bill",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/bill/{billId}")
    public List<BillDetailDto> findByBillId(@PathVariable String billId){
        List<BillDetail> billDetails = billDetailService.findByBillId(billId);
        return billDetails.stream()
                .map(billDetail -> billDetailService.getBillDetailDto(billDetail)) // hoặc BillDetailDto.fromEntity(...)
                .toList();
    }

    @Operation(summary = "Tìm kiếm người dùng dựa trên roles",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public BillDetailDto findById(@PathVariable String id) {
        BillDetail billDetail = billDetailService.findById(id);
        return billDetailService.getBillDetailDto(billDetail);
    }

    @Operation(summary = "Tạo bill detail",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{id}")
    public BillDetailDto create(@RequestBody BillDetailCreateRequest req) {
        BillDetail billDetail = billDetailService.create(req);
        return billDetailService.getBillDetailDto(billDetail);
    }

    @Operation(summary = "Xóa bill detail",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        billDetailService.delete(id);
    }

}
