package com.Nong.treading.controller;

import com.Nong.treading.domain.PaymentMethod;
import com.Nong.treading.modal.PaymentOrder;
import com.Nong.treading.modal.User;
import com.Nong.treading.response.PaymentResponse;
import com.Nong.treading.service.PaymentService;
import com.Nong.treading.service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt)
            throws Exception,
            RazorpayException,
            StripeException {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLing(user,amount);
        }
        else {
            paymentResponse = paymentService.createStripePaymentLing(user, amount, order.getId());
        }
        return new ResponseEntity<>(paymentResponse,HttpStatus.CREATED);


    }

}
