package com.wallet.app.controller;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.AdminException;
import com.wallet.app.exception.WalletException;
import com.wallet.app.service.WalletService;
import com.wallet.app.service.WalletServiceImpl;

import java.util.Scanner;

public class Controller {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin";
    public static WalletService walletService = new WalletServiceImpl();
    public static Scanner numScan = new Scanner(System.in);
    public static Scanner strScan = new Scanner(System.in);


    public static void showBalance(Integer walletId){
        try {
            System.out.println("Your balance is: "+walletService.showWalletBalance(walletId));

        } catch (WalletException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addFund(Integer loginId) throws WalletException{
        System.out.print("Enter the amount to deposit: ");
        Double fundToAdd = numScan.nextDouble();
        System.out.print("Amount added successfully\nYour current balance: ");
        System.out.println(walletService.addFundsToWallet(loginId,fundToAdd));
    }

    public static void withdrawFund(Integer loginId){
        System.out.print("Enter the amount to withdraw: ");
        Double fundToWithdraw = numScan.nextDouble();
        try {
            Double remainingBalance = walletService.withdrawFunds(loginId,fundToWithdraw);
            System.out.println("Amount withdrawn successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            showBalance(loginId);
        }

    }

    public static void transferFund(Integer loginId){
        System.out.print("Enter the wallet Id to be transferred: ");
        Integer toId = numScan.nextInt();
        System.out.print("Enter the amount to transfer: ");
        Double amount = numScan.nextDouble();
        try{
            walletService.fundTransfer(loginId,toId,amount);
            System.out.println("Amount transfer successful!");
        } catch (WalletException e) {
            System.out.println(e.getMessage());;
        }
        finally {
            showBalance(loginId);
        }
    }

    public static void deleteWallet(Integer loginId){
        System.out.print("enter your password: ");
        String password = strScan.next();
        try {
            walletService.unRegisterWallet(loginId,password);
            System.out.println("Deleted Wallet successfully!");
        } catch (WalletException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printAllWallet() throws AdminException{
        System.out.print("Admin username: ");
        String adminName = strScan.next();
        System.out.print("Admin Password: ");
        String adminPassword = strScan.next();

        if(adminName.equals(ADMIN_USER) && adminPassword.equals(ADMIN_PASS)) {
            for (Wallet wal : walletService.getAllWallets().values()) {
                System.out.println(wal);
            }
        }
        else throw new AdminException("Invalid Admin");
    }



    public static void main(String[] args) {

        //IdGenerator idGenerator = new IdGenerator();

        while (true) {
        try {
            System.out.println("[*][*][*] Welcome page [*][*][*]");
            System.out.println("Choose:\n1 - Register\n2 - Login\n3 - Exit\n4 - Show all users");
            Integer homeInput = numScan.nextInt();

            if (homeInput == 3) break;

            else if (homeInput == 1) {
                System.out.println("[*][*][*] Registration page [*][*][*]");
                Integer registerId;           //= idGenerator.generateId();
                String registerName;
                Double registerBalance;
                String registerPassword;
                System.out.println("Enter the details below");
                System.out.print("Enter yor Id: ");
                registerId = numScan.nextInt();
                System.out.print("Enter your name: ");
                registerName = strScan.next();
                System.out.print("Set  password: ");
                registerPassword = strScan.next();
                System.out.print("Enter the initial amount to deposit: ");
                registerBalance = numScan.nextDouble();

                //System.out.println("Id generator test: "+registerId);

                walletService.registerWallet(new Wallet(registerId, registerName, registerBalance, registerPassword));
                System.out.println("Wallet created successfully");
                System.out.println("Your wallet Id is: " + registerId);
                System.out.println("your account balance is: " + registerBalance);
            }

           else if (homeInput == 4) printAllWallet();

            else {
                while (true) {
                    Integer loginId;
                    String loginPassword;
                    System.out.println("[*][*][*] Login Page [*][*][*]" );
                    System.out.print("Enter wallet ID: ");
                    loginId = numScan.nextInt();
                    System.out.print("Enter password: ");
                    loginPassword = strScan.next();
                    Boolean isValid = walletService.login(loginId, loginPassword);

                    if (isValid) {
                        Integer loginInput;
                        System.out.println("Select action               wallet ID: "+loginId);
                        System.out.println("1 - Show wallet balance\n2 - Add fund\n3 - withdraw fund\n4 - Transfer fund \n5 - Delete wallet\n6 - back");
                        loginInput = numScan.nextInt();
                        if (loginInput == 6) break;
                        switch (loginInput) {
                            case (1):
                                showBalance(loginId);
                                break;
                            case (2):
                                addFund(loginId);
                                break;
                            case (3):
                                withdrawFund(loginId);
                                break;
                            case (4):
                                transferFund(loginId);
                                break;
                            case (5):
                                deleteWallet(loginId);
                                break;
                            default:
                                System.out.println("Enter valid input");
                        }
                    }

                    else System.out.println("Incorrect password!");

                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        }
    }

}
