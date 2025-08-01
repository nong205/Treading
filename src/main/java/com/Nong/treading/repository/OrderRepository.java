package com.Nong.treading.repository;

import com.Nong.treading.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
     List<Order> findByUserId(Long userId) throws Exception;
}
