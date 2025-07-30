package com.Nong.treading.repository;

import com.Nong.treading.modal.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId); // chú ý dùng user_Id
}

