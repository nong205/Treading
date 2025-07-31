package com.Nong.treading.service;


import com.Nong.treading.modal.Order;
import com.Nong.treading.modal.User;
import com.Nong.treading.modal.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
//    Wallet walletToWalletTransfer(Wallet senderWallet, User receiver, Long amount);
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
