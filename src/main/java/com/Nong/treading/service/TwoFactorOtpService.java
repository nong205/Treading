package com.Nong.treading.service;

import com.Nong.treading.modal.TwoFactorOTP;
import com.Nong.treading.modal.User;

public interface TwoFactorOtpService {
    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);
    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP,String otp);
    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}
