package com.example.OpenSky.dtos.UserVoucher;

import com.example.OpenSky.entities.User;
import com.example.OpenSky.entities.UserVoucher;
import com.example.OpenSky.entities.Voucher;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVoucherDto {
    private String id;

    private boolean isUsed = false;

    private User user;

    private Voucher voucher;

    public static UserVoucherDto fromEntity(UserVoucher userVoucher) {
        return new UserVoucherDto(
                userVoucher.getId(),
                userVoucher.isUsed(),
                userVoucher.getUser(),
                userVoucher.getVoucher()
        );
    }
}
