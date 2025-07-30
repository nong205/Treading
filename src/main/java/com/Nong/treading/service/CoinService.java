package com.Nong.treading.service;

import com.Nong.treading.modal.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page);

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTradingCoins() throws Exception;

}
