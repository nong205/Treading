package com.Nong.treading.request;

import com.Nong.treading.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType; // "BUY" or "SELL"
}
