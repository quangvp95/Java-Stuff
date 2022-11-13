package vn.funix.xM01892.java.asm03.models;

import vn.funix.xM01892.java.asm02.models.Account;

public class SavingsAccount extends Account {
    public static final int SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;

    @Override
    public void log(double amount) {
        System.out.println("+------+--------------------------+------+");
        System.out.println("     BIEN LAI GIAO DICH SAVINGS");
        System.out.println("NGAY G/D:  "+ String.format("%26s", Transaction.getDateTime()));
        System.out.println("ATM ID:         DIGITAL-BANK-ATM 2022");
        System.out.println("SO TK:     "+ String.format("%26s", getAccountNumber()));
        System.out.println("SO TIEN:   "+ String.format("%25.0f", amount) + "đ");
        System.out.println("SO DU:     "+ String.format("%25.0f", getBalance()) + "đ");
        System.out.println("PHI + VAT:                         0đ");
        System.out.println("+------+--------------------------+------+");
    }

    @Override
    public boolean withdraw(double amount) {
        if (!isAccepted(amount)) {
            addTransaction(new Transaction(getAccountNumber(), amount, false));
            return false;
        }
        setBalance(getBalance() - amount);
        addTransaction(new Transaction(getAccountNumber(), amount, true));
        log(amount);
        return true;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (amount < 50000) {
            System.out.println("So tien rut 1 lan phai lon hon 50.000 vnd");
            return false;
        }
        if (!isPremium() && amount > SAVINGS_ACCOUNT_MAX_WITHDRAW) {
            System.out.println("So tien rut 1 lan khong duoc vuot qua 5.000.000 vnd");
            return false;
        }
        if (getBalance() - amount < 50000) {
            System.out.println("So du con lai phai lon hon 50.000 vnd");
            return false;
        }
        if (amount % 10000 != 0) {
            System.out.println("So tien rut phai la boi so cua 10.000 vnd");
            return false;
        }

        return true;
    }

    @Override
    public String getType() {
        return "SAVINGS";
    }
}
