package com.example.OpenSky.services;

import com.example.OpenSky.entities.User;
import com.example.OpenSky.entities.UserVoucher;
import com.example.OpenSky.entities.Voucher;
import com.example.OpenSky.repositories.UserVoucherRepository;
import com.example.OpenSky.requests.UserVoucher.UserVoucherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherService {
    @Autowired
    UserVoucherRepository userVoucherRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    UserService userService;

    public Page<UserVoucher> findByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userVoucherRepository.findByUser_Id(userId, pageable);
    }

    public Page<UserVoucher> findByVoucherId(String voucherId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userVoucherRepository.findByVoucher_Id(voucherId, pageable);
    }

    public UserVoucher findById(String id) {
        return userVoucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this UserVoucher"));
    }

    public UserVoucher create(UserVoucherRequest req) {
        UserVoucher userVoucher = new UserVoucher();
        User user = userService.findById(req.getUserId());
        Voucher voucher = voucherService.findById(req.getVoucherId());
        userVoucher.setUser(user);
        userVoucher.setVoucher(voucher);
        return userVoucherRepository.save(userVoucher);
    };

    public void setUsedVoucher(String id) {
        UserVoucher userVoucher = findById(id);
        userVoucher.setUsed(true);
        userVoucherRepository.save(userVoucher);
    }

    public void delete(String userId, String voucherId) {
        User user = userService.findById(userId);
        Voucher voucher = voucherService.findById(voucherId);
        userVoucherRepository.deleteByUser_IdAndVoucher_Id(userId, voucherId);
    }
}
