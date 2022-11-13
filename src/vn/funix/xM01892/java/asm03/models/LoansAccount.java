package vn.funix.xM01892.java.asm03.models;

import vn.funix.xM01892.java.asm02.models.Account;

public class LoansAccount extends Account {
    public static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    public static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    public static final int LOAN_ACCOUNT_MAX_BALANCE = 100000000;

    @Override
    public void log(double amount) {
        double fee = amount * (isPremium() ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE);
        System.out.println("+------+--------------------------+------+");
        System.out.println("     BIEN LAI GIAO DICH LOAN");
        System.out.println("NGAY G/D:  "+ String.format("%27s", Transaction.getDateTime()));
        System.out.println("ATM ID:          DIGITAL-BANK-ATM 2022");
        System.out.println("SO TK:     "+ String.format("%27s", getAccountNumber()));
        System.out.println("SO TIEN:   "+ String.format("%26.0f", amount) + "đ");
        System.out.println("SO DU:     "+ String.format("%26.0f", getBalance() + LOAN_ACCOUNT_MAX_BALANCE) + "đ");
        System.out.println("PHI + VAT: "+ String.format("%26.0f", fee) + "đ");
        System.out.println("+------+--------------------------+------+");
    }

    @Override
    public boolean withdraw(double amount) {
        if (!isAccepted(amount)) {
            addTransaction(new Transaction(getAccountNumber(), -amount, false));
            return false;
        }
        double fee = amount * (isPremium() ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE);
        setBalance(getBalance() - amount - fee);
        addTransaction(new Transaction(getAccountNumber(), -amount, true));
        log(amount);
        return true;
    }

    @Override
    public boolean isAccepted(double amount) {
        double fee = amount * (isPremium() ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE);
        if (getBalance() - amount - fee < (50000 - LOAN_ACCOUNT_MAX_BALANCE)) {
            System.out.println("Vuot qua han muc cho phep");
            return false;
        }
        return true;
    }

    @Override
    public String getType() {
        return "LOAN";
    }
}
