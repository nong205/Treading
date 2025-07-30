package com.Nong.treading.service;

import com.Nong.treading.domain.VerificationType;
import com.Nong.treading.modal.ForgotPasswordToken;
import com.Nong.treading.modal.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    VerificationType verificationType,
                                    String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);


}
