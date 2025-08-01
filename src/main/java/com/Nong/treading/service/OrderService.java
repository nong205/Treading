package com.Nong.treading.service;

import com.Nong.treading.domain.OrderType;
import com.Nong.treading.modal.Coin;
import com.Nong.treading.modal.Order;
import com.Nong.treading.modal.OrderItem;
import com.Nong.treading.modal.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType) throws Exception;
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) throws Exception;
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
