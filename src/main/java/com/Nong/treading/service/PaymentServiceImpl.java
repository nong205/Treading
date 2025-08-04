package com.Nong.treading.service;

import com.Nong.treading.domain.PaymentMethod;
import com.Nong.treading.domain.PaymentOrderStatus;
import com.Nong.treading.modal.PaymentOrder;
import com.Nong.treading.modal.User;
import com.Nong.treading.repository.PaymentOrderRepository;
import com.Nong.treading.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;


    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;

    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) throws Exception {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment order not found with id: " + id));
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);
                Payment payment = razorpay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if (status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepository.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLing(User user, Long amount) throws Exception {
        Long Amount = amount * 100;
        try{
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);

            // Tạo một đối tượng JSON với các tham số yêu cầu liên kết thanh toán
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            // Thêm thông tin người dùng vào yêu cầu
            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());

            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            // Tạo một đối tượng JSON với cài đặt thông báo
            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify",notify);

            // Tạo một đối tượng JSON với cài đặt nhắc nhở
            paymentLinkRequest.put("reminder_enable", true);

            // Thiết lập url gọi lại và phương thức
            paymentLinkRequest.put("callback_url", "http://localhost:8082/wallet");
            paymentLinkRequest.put("callback_method", "get");

            //tạo liên kết thanh toán bằng phương thức paymentLink.create()
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
              String paymentLinkId = payment.get("id");
              String paymentLinkUrl = payment.get("short_url");

              PaymentResponse res = new PaymentResponse();
                res.setPayment_url(paymentLinkId);

                return res;
        }catch (RazorpayException e){
            System.out.println("Error creating payment link: "+e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }

    @Override
    public PaymentResponse createStripePaymentLing(User user, Long amount, Long orderId) throws StripeException {
        // Logic tạo liên kết thanh toán Stripe
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8082/wallet?orderId=" + orderId)
                .setCancelUrl("http://localhost:8082/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                                .setUnitAmount(amount * 100) // Stripe expects amount in cents
                                .setProductData(SessionCreateParams.
                                        LineItem.
                                        PriceData.
                                        ProductData.
                                        builder()
                                        .setName("Top up Wallet")
                                        .build())
                                .build())
                           .build())
                      .build();
            Session session = Session.create(params);
            System.out.println("session _______" + session);
            PaymentResponse res = new PaymentResponse();
            res.setPayment_url(session.getUrl());

        return res;
    }
}
