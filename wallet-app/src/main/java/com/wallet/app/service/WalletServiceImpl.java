package com.wallet.app.service;

import com.wallet.app.dao.WalletDao;
import com.wallet.app.dao.WalletDaoImpl;
import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;
//import com.wallet.app.exception.WalletRepositoryException;

import java.util.Map;

public class WalletServiceImpl implements WalletService {

    private WalletDao walletRepository = new WalletDaoImpl();

    public Wallet registerWallet(Wallet newWallet) throws WalletException {
        return this.walletRepository.addWallet(newWallet);
    }

    public Boolean login(Integer walletId, String password) throws WalletException {
        Wallet fetchedWallet = this.walletRepository.getWalletById(walletId);
        return(fetchedWallet.getPassword().compareTo(password)==0);
    }

    public Double addFundsToWallet(Integer walletId, Double amount) throws WalletException {
        Wallet fetchedWallet = this.walletRepository.getWalletById(walletId);
        fetchedWallet.setBalance(fetchedWallet.getBalance()+amount);
        this.walletRepository.updateWallet(fetchedWallet);
        return fetchedWallet.getBalance();
    }

    public Double showWalletBalance(Integer walletId) throws WalletException {
        return this.walletRepository.getWalletById(walletId).getBalance();
    }

    public Double fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException {
        Wallet fetchedFromWallet = this.walletRepository.getWalletById(fromId);
        Wallet fetchedToWallet = this.walletRepository.getWalletById(toId);

        if(fetchedFromWallet.getBalance()-amount<0) throw new WalletException("You don't have enough balance to transfer");
        else{
            fetchedFromWallet.setBalance(fetchedFromWallet.getBalance()-amount);
            this.walletRepository.updateWallet(fetchedFromWallet);

            fetchedToWallet.setBalance((fetchedToWallet.getBalance()+amount));
            this.walletRepository.updateWallet(fetchedToWallet);
        }
        return fetchedFromWallet.getBalance();
    }

    public Wallet unRegisterWallet(Integer walletId, String password) throws WalletException {
        if(this.login(walletId,password)){
            return this.walletRepository.deleteWalletById(walletId);
        }
        else throw new WalletException("Incorrect password");
    }

    @Override
    public Double withdrawFunds(Integer walletId, Double amount) throws WalletException {
        Wallet tempWallet = walletRepository.getWalletById(walletId);
        if (tempWallet.getBalance() - amount < 0) throw new WalletException("Balance is insufficient!");
        tempWallet.setBalance(tempWallet.getBalance() - amount);
        walletRepository.updateWallet(tempWallet);
        return tempWallet.getBalance();
    }

    public Map<Integer,Wallet> getAllWallets(){
        return this.walletRepository.getWallets();
    }

}