package com.Nong.treading.service;

import com.Nong.treading.domain.VerificationType;
import com.Nong.treading.modal.User;
import com.Nong.treading.modal.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeByUserId(Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);

}
