package com.Nong.treading.controller;

import com.Nong.treading.modal.Coin;
import com.Nong.treading.modal.Order;
import com.Nong.treading.modal.User;
import com.Nong.treading.request.CreateOrderRequest;
import com.Nong.treading.service.CoinService;
import com.Nong.treading.service.OrderService;
import com.Nong.treading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private Wa

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(
                coin,
                req.getQuantity(),
                req.getOrderType(),
                user
        );
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception {
       if ()
    }
}
