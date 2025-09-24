package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Voucher.VoucherDto;
import com.example.OpenSky.entities.Voucher;
import com.example.OpenSky.requests.Voucher.VoucherCreateRequest;
import com.example.OpenSky.requests.Voucher.VoucherUpdateRequest;
import com.example.OpenSky.services.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @Operation(summary = "Lấy ra tất cả voucher")
    @GetMapping
    public Page<VoucherDto> getVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Voucher> vouchers = voucherService.getVouchers(page,size);
        return vouchers.map(VoucherDto::fromEntity);
    }

    @Operation(summary = "Lấy voucher theo id")
    @GetMapping("/{id}")
    public VoucherDto getVoucher(@PathVariable String id) {
        Voucher voucher = voucherService.findById(id);
        return VoucherDto.fromEntity(voucher);
    }

    @GetMapping("/search")
    public Page<VoucherDto> searchVouchers(
            @RequestParam String date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Voucher> vouchers = voucherService.searchVoucherByDate(date, page, size);
        return vouchers.map(VoucherDto::fromEntity);
    }

    @Operation(summary = "Tạo voucher",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public VoucherDto createVoucher(@RequestBody VoucherCreateRequest req) {
        Voucher voucher = voucherService.create(req);
        return VoucherDto.fromEntity(voucher);
    }

    @Operation(summary = "Cập nhật voucher",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PutMapping("/{id}")
    public VoucherDto updateVoucher(
            @PathVariable String id,
            @Valid @RequestBody VoucherUpdateRequest req) {
        Voucher voucher = voucherService.update(id, req);
        return VoucherDto.fromEntity(voucher);
    }
}
