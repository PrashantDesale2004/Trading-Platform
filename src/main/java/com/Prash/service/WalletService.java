package com.Prash.service;

import com.Prash.model.Order;
import com.Prash.model.User;
import com.Prash.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user) throws Exception;
    Wallet addBalanced(Wallet wallet, Long Money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
