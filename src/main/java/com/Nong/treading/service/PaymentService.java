package com.Nong.treading.service;

import com.Nong.treading.domain.PaymentMethod;
import com.Nong.treading.modal.PaymentOrder;
import com.Nong.treading.modal.User;
import com.Nong.treading.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(
            User user,
            Long amount,
            PaymentMethod paymentMethod
    ) throws Exception;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProceedPaymentOrder(
            PaymentOrder paymentOrder,
            String paymentId
    ) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLing(
            User user,
            Long amount
    ) throws Exception;

    PaymentResponse createStripePaymentLing (
            User user,
            Long amount,
            Long orderId
    ) throws StripeException;


}
