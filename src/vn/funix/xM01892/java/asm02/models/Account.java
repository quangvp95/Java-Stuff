package vn.funix.xM01892.java.asm02.models;

public class Account {
    private String accountNumber;
    private double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isPremium() {
        return balance > 10000000;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + balance;
    }
}
