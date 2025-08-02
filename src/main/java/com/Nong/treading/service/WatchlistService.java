package com.Nong.treading.service;

import com.Nong.treading.modal.Coin;
import com.Nong.treading.modal.User;
import com.Nong.treading.modal.Watchlist;

public interface WatchlistService {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
