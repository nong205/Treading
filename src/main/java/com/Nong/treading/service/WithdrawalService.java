package com.Nong.treading.service;

import com.Nong.treading.modal.User;
import com.Nong.treading.modal.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestyWithdrawal(Long amount, User user);
    Withdrawal procedWithwithdrawal(Long withdrawalId, Boolean accept) throws Exception;
    List<Withdrawal> getUserWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequest();
}
