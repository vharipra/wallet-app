package com.wallet.app.service;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;

import java.util.Map;

public interface WalletService {

    Wallet registerWallet(Wallet newWallet) throws WalletException;

    Boolean login(Integer walletId,String password)throws WalletException;

    Double addFundsToWallet(Integer walletId, Double amount) throws WalletException;

    Double showWalletBalance(Integer walletId)throws WalletException;

    Double fundTransfer(Integer fromId, Integer toId, Double amount)throws WalletException;

    Wallet unRegisterWallet(Integer walletId,String password)throws WalletException;

    Double withdrawFunds(Integer WalletID, Double amount) throws WalletException;

    Map<Integer,Wallet> getAllWallets();
}