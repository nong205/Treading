package com.Nong.treading.service;

import com.Nong.treading.modal.PaymentDetails;
import com.Nong.treading.modal.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(
            String accountNumber,
            String accountHolderName,
            String ifsc,
            String bankName,
            User user
            ) throws Exception;

    public PaymentDetails getUserPaymentDetails(User user) throws Exception;
}
