package vn.funix.xM01892.java.asm02.models;

import vn.funix.xM01892.java.asm03.base.ReportService;
import vn.funix.xM01892.java.asm03.base.Withdraw;
import vn.funix.xM01892.java.asm03.models.Transaction;

import java.util.ArrayList;

public class Account implements ReportService, Withdraw {
    private String accountNumber;
    private double balance;
    private final ArrayList<Transaction> transactions = new ArrayList<>();

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
        return balance >= 10000000;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public String getType() {
        return "";
    }

    @Override
    public String toString() {
        return accountNumber + " |  " + String.format("%19s", getType()) + " |           " + String.format("%15.0f", balance) + "đ";
    }

    @Override
    public void log(double amount) {

    }

    @Override
    public boolean withdraw(double amount) {
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        return false;
    }

    public void showHistory() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
