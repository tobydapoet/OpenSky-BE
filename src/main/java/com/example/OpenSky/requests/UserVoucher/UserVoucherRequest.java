package com.example.OpenSky.requests.UserVoucher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVoucherRequest {

    private String userId;

    private String voucherId;

}
