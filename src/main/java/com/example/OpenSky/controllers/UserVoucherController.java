package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.UserVoucher.UserVoucherDto;
import com.example.OpenSky.entities.UserVoucher;
import com.example.OpenSky.requests.UserVoucher.UserVoucherRequest;
import com.example.OpenSky.services.UserVoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_vouchers")
public class UserVoucherController {
    @Autowired
    private UserVoucherService userVoucherService;

    @Operation(summary = "Xem voucher mà người dùng có",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/user/{id}")
    public Page<UserVoucherDto> findByUserId(
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserVoucher> userVouchers = userVoucherService.findByUserId(userId, page, size);
        return userVouchers.map(UserVoucherDto::fromEntity);
    }

    @Operation(summary = "Lấy tất cả người dùng có voucherId",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @GetMapping("/voucher/{id}")
    public Page<UserVoucherDto> findByVoucherId(
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserVoucher> userVouchers = userVoucherService.findByVoucherId(userId, page, size);
        return userVouchers.map(UserVoucherDto::fromEntity);
    }

    @Operation(summary = "lấy voucher",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public UserVoucherDto create(@RequestBody UserVoucherRequest req){
        UserVoucher userVoucher = userVoucherService.create(req);
        return UserVoucherDto.fromEntity(userVoucher);
    }

    @Operation(summary = "Xóa voucher người dùng",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @DeleteMapping("/{id}")
    public void delete(
            @RequestParam String userId,
            @RequestParam String voucherId
    ){
        userVoucherService.delete(userId, voucherId);
    }
}
