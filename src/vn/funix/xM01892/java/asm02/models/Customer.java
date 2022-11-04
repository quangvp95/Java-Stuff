package vn.funix.xM01892.java.asm02.models;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean isPremium() {
        for (Account acc : accounts) {
            if (acc.isPremium())
                return true;
        }
        return false;
    }

    public void addAccount(Account account) {
        if (account == null) return;
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(account.getAccountNumber()))
                return;
        }
        accounts.add(account);
    }

    public double getBalance() {
        double total = 0;
        for (Account acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    private String getCustomerType() {
        return isPremium() ? "Premium" : "Normal";
    }

    public void displayInformation() {
        System.out.println(getCustomerId() + " | " + String.format("%20s", getName())
                + " | " + String.format("%7s", getCustomerType())
                + " | " + String.format("%15.0f", getBalance())+ "đ");
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.println((i + 1) + "   " + acc);
        }
    }
}
