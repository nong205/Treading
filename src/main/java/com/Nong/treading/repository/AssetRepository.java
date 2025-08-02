package com.Nong.treading.repository;

import com.Nong.treading.modal.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByUserId(Long userId);

    Asset findByUserIdAndCoinId(Long userId, String coinId);
//    Asset findByIdAndUserId(Long assetId, Long userId);
//    void deleteByIdAndUserId(Long assetId, Long userId);
//    List<Asset> findByCoinId(String coinId);
}
