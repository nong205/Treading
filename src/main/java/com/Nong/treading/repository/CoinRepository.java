package com.Nong.treading.repository;

import com.Nong.treading.modal.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
