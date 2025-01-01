package com.Prash.service;

import com.Prash.Domain.OrderType;
import com.Prash.Repository.WalletRepository;
import com.Prash.model.Order;
import com.Prash.model.User;
import com.Prash.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletServiceImpl implements WalletService{
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet==null){
            Wallet newwallet = new Wallet();
            wallet.setUser(user);

        }
        return wallet;
    }

    @Override
    public Wallet addBalanced(Wallet wallet, Long Money) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(Money));
        wallet.setBalance(newBalance);


        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet =walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet  = getUserWallet(sender);
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0 ){
            throw new Exception("Insufficient balance....");
        }
        BigDecimal sederBalance = senderWallet
                .getBalance()
                .subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(sederBalance);
        walletRepository.save(senderWallet);
        BigDecimal receiverBalance = receiverWallet.getBalance()
                .add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepository.save(receiverWallet);

        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet =getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY)){
           BigDecimal newbal = wallet.getBalance().subtract(order.getPrice());
           if(newbal.compareTo(order.getPrice())<0){
               throw new Exception("Insufficient funds for this transaction");
           }

           wallet.setBalance(newbal);
        }else {
            BigDecimal newbal = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newbal);
        }
        walletRepository.save(wallet);
        return wallet;
    }
}
